package de.buhl.postgrespringdata;


import de.buhl.postgrespringdata.repository.UserRepo;
import de.buhl.postgrespringdata.service.UserService;
import de.buhl.postgrespringdata.util.IdService;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {UserRepositoryTCIntegrationTest.Initializer.class})
class IntegrationAccountUserDatabaseTest extends UserRepositoryCommonIntegrationTests{

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11.1")
            .withDatabaseName("postgres").withUsername("postgres").withPassword("123456789")
            .withInitScript("test-data.sql");

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IdService idService;

    @Autowired
    private UserService userService;

    @BeforeAll
    private static void setup() throws IOException {
        postgreSQLContainer.start();
    }
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("jdbc:postgresql://localhost:5432/postgres", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("postgres", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("123456789", postgreSQLContainer::getPassword);
    }
    @AfterAll
    private static void cleanup() throws IOException {
        postgreSQLContainer.stop();
    }

    //Test Spring Data JPA connection to database
    @Test
    @Order(value = 1)
    void testConnectionToDatabase() {
        Assertions.assertNotNull(userRepo);
    }

    //Fill test DB with Test Data
    @Test
    @Order(value = 2)
    void testGetAllUsers() throws Exception {
        //GIVEN
        String expected = ("""
                [
                {
                "id":"1",
                "username": "test1",
                "password": "12345",
                "userInfo":{
                          "email": "1234@gmail.com",
                          "firstName":"Max",
                          "lastName":"Mustermann",
                          "street": "musterstraße 1",
                          "city": "Musterstadt 60599",
                          "country": "Deutschland",
                          "companyName": "Musterfirma"
                          },
                taxInfo:{
                         "taxId":"123456789",
                         "salary": 54000,
                         "taxClass": 1,
                         "taxOffice":"Finanzamt Köln"
                         }
                },
                {
                "id":"2", 
                "username": "test2",
                "password": "12345",
                "userInfo":{
                          "email": "blabla123@gmail.com",
                           "firstName":"Julius",
                            "lastName":"Caesar",
                            "street": "musterstraße 2",
                            "city": "Musterstadt 60599",
                            "country": "Deutschland",
                            "companyName": "Musterfirma"
                            },
                taxInfo:{
                          "taxId:""123456789",
                         "salary": 54000,
                         "taxClass": 1,
                         "taxOffice":"Finanzamt Köln"
                         }
               
                }]""");
        //WHEN
        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                 [
                                {
                                "id":"1",
                                "username": "test1",
                                "password": "12345",
                                "userInfo":{
                          "email": "1234@gmail.com",
                          "firstName":"Max",
                          "lastName":"Mustermann",
                          "street": "musterstraße 1",
                          "city": "Musterstadt 60599",
                          "country": "Deutschland",
                          "companyName": "Musterfirma"
                          },
                                taxInfo:{
                         "taxId":"123456789",
                         "salary": 54000,
                         "taxClass": 1,
                         "taxOffice":"Finanzamt Köln"
                         }
                                },
                                {
                                "id":"2",
                                "username": "test2",
                                "password": "12345",
                                "userInfo":{
                          "email": "blabla123@gmail.com",
                           "firstName":"Julius",
                            "lastName":"Caesar",
                            "street": "musterstraße 2",
                            "city": "Musterstadt 60599",
                            "country": "Deutschland",
                            "companyName": "Musterfirma"
                            },
                                taxInfo:{
                          "taxId:""123456789",
                         "salary": 54000,
                         "taxClass": 1,
                         "taxOffice":"Finanzamt Köln"
                         }
               
                }
                                ]

                 """))
                //THEN
                .andExpect(status().isCreated()).andReturn();

                assertEquals(2, userRepo.findAll().size());
        }


    }



