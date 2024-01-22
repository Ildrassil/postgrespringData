package de.buhl.postgrespringdata;


import de.buhl.postgrespringdata.repository.UserRepo;
import de.buhl.postgrespringdata.service.UserService;
import de.buhl.postgrespringdata.util.IdService;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class IntegrationAccountUserDatabaseTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11.1")
            .withDatabaseName("postgres").withUsername("postgres").withPassword("123456789");

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IdService idService;

    @Autowired
    private UserService userService;

    @BeforeAll
    static void setup() throws IOException {
        postgreSQLContainer.start();
    }
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
    @AfterAll
    static void cleanup() {
        postgreSQLContainer.stop();
    }

    //Test Spring Data JPA connection to database
    @Test
    void testConnectionToDatabase() {
        Assertions.assertNotNull(userRepo);
    }

    //Fill test DB with Test Data
    @Test
    void testGetAllUsers() throws Exception {
        //GIVEN
        String actual = ("""
               
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
                "taxInfo":{
                         "taxId":"123456789",
                         "salary": 54000,
                         "taxClass": 1,
                         "taxOffice":"Finanzamt Köln"
                         }
                }""");
        //WHEN
        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actual))

                //THEN
                .andExpect(status().isCreated());


                assertEquals(1, userRepo.findAll().size());
        }


    }



