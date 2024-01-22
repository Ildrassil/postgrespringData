package de.buhl.postgrespringdata;


import de.buhl.postgrespringdata.model.dto.UserRequest;
import de.buhl.postgrespringdata.model.entity.AccountUser;
import de.buhl.postgrespringdata.model.entity.submodel.TaxInfo;
import de.buhl.postgrespringdata.model.entity.submodel.UserInfo;
import de.buhl.postgrespringdata.repository.UserRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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


    @BeforeAll
    static void setup() {
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

    //Test Spring Data JPA connection zur Datenabnk
    @Test
    void testConnectionToDatabase() {
        Assertions.assertNotNull(userRepo);
    }


    //Testen ob die Daten gespeichert werden
    @Test
    void createUser() throws Exception {
        //GIVEN
        String TestUser1 = ("""
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
                        .content(TestUser1))


                //THEN
                .andExpect(status().isCreated());


        assertEquals(3, userRepo.findAll().size());
    }


    //Testen ob User gespeichert wird und mit der ID gefunden werden kann
    @Test
    void testGetUserById() throws Exception {
        //GIVEN
        UserRequest userRequest = new UserRequest("Victoria", "12345",
                new UserInfo("1234@gmail.com",
                        "Max",
                        "Mustermann",
                        "musterstraße 1",
                        "Musterstadt 60599",
                        "Deutschland",
                        "Musterfirma"),
                new TaxInfo(
                        "123456789",
                        54000,
                        1,
                        "Finanzamt Köln",
                        null,
                        null));


        userRepo.save(new AccountUser("4",
                userRequest.userName(), userRequest.password(),
                userRequest.userInfo(), userRequest.taxInfo()));

        // WHEN
        MvcResult actual = mockMvc.perform(get("/api/user/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json().build().writeValueAsString(userRequest)))

                //THEN
                .andExpect(status().isAccepted()).andReturn();

        assertEquals(202, actual.getResponse().getStatus());

    }

    //Testen ob alle User gefunden werden können
    @Test
    void testGetAllUsers() throws Exception {
        //GIVEN
        AccountUser user = new AccountUser("1", "test1", "12345",
                new UserInfo(
                        "123@mail.com",
                        "Max",
                        "Mustermann",
                        "musterstraße 1",
                        "Musterstadt 60599",
                        "Deutschland",
                        "Musterfirma"),
                new TaxInfo(
                        "123456789",
                        54000,
                        1,
                        "Finanzamt Köln",
                        null,
                        null));

        AccountUser user1 = new AccountUser("2", "test2", "12345",
                new UserInfo(
                        "123@mail.com",
                        "Jennifer",
                        "Mustermann",
                        "musterstraße 1",
                        "Musterstadt 60599",
                        "Deutschland",
                        "Musterfirma"),
                new TaxInfo(
                        "123456789",
                        54000,
                        1,
                        "Finanzamt Köln",
                        null,
                        null));


        userRepo.save(user);
        userRepo.save(user1);

        //WHEN
        MvcResult actual = mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                //THEN
                .andExpect(status().isAccepted()).andReturn();

        assertEquals(202, actual.getResponse().getStatus());

    }

    //Testen ob User aktualisiert werden kann
    @Test
    void testUpdateUser() throws Exception {
        //GIVEN
        UserRequest userRequest = new UserRequest("test1", "12345",
                new UserInfo(
                        "1213@mail.com",
                        "Max",
                        "Mustermann",
                        "musterstraße 1",
                        "Musterstadt 60599",
                        "Deutschland",
                        "Musterfirma"),
                new TaxInfo(
                        "123456789",
                        54000,
                        1,
                        "Finanzamt Köln",
                        List.of(),
                        List.of()));

        AccountUser user = new AccountUser("1", "test1", "12345",
                new UserInfo(
                        "1213@mail.com",
                        "Max",
                        "Mustermann",
                        "musterstraße 1",
                        "Musterstadt 60599",
                        "Deutschland",
                        "BuhlData"),
                new TaxInfo(
                        "123456789",
                        54000,
                        2,
                        "Finanzamt Köln",
                        null,
                        null));

        //WHEN
        userRepo.save(user);
        MvcResult result = mockMvc.perform(put("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json().build().writeValueAsString(userRequest)))
                //THEN
                .andExpect(status().isAccepted()).andReturn();
        AccountUser expected = new AccountUser("1", userRequest.userName(), userRequest.password(),
                userRequest.userInfo(), userRequest.taxInfo());
        assertEquals(expected, userRepo.findById("1").get());
        assertEquals(202, result.getResponse().getStatus());

    }

    //Testen ob User gelöscht werden kann
    @Test
    void testDeleteUser() throws Exception {
        //GIVEN
        AccountUser user = new AccountUser("1", "test1", "12345",
                new UserInfo(
                        "1213@mail.com",
                        "Max",
                        "Mustermann",
                        "musterstraße 1",
                        "Musterstadt 60599",
                        "Deutschland",
                        "BuhlData"),
                new TaxInfo(
                        "123456789",
                        54000,
                        2,
                        "Finanzamt Köln",
                        null,
                        null));

        userRepo.save(user);
        //WHEN
        MvcResult result = mockMvc.perform(delete("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                //THEN
                .andExpect(status().isNoContent()).andReturn();
        assertEquals(204, result.getResponse().getStatus());
        assertEquals(1, userRepo.findAll().size());


    }

    @Test
    void testGetUserWithWrongPassword() throws Exception {
        //GIVEN
        UserRequest userRequest = new UserRequest("test1", "12345",
                new UserInfo(
                        "123@mail.com",
                        "Max",
                        "Mustermann",
                        "musterstraße 1",
                        "Musterstadt 60599",
                        "Deutschland",
                        "Musterfirma"),
                new TaxInfo(
                        "123456789",
                        54000,
                        1,
                        "Finanzamt Köln",
                        null,
                        null));

        AccountUser user = new AccountUser("1", "test1", "1441415",
                new UserInfo(
                        "123@mail.com",
                        "Max",
                        "Mustermann",
                        "musterstraße 1",
                        "Musterstadt 60599",
                        "Deutschland",
                        "Musterfirma"),
                new TaxInfo(
                        "123456789",
                        54000,
                        1,
                        "Finanzamt Köln",
                        null,
                        null));

        userRepo.save(user);

        //WHEN
        MvcResult result = mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json().build().writeValueAsString(userRequest)))
                //THEN
                .andExpect(status().isBadRequest()).andReturn();
        assertEquals(400, result.getResponse().getStatus());

    }

    @Test
    void testGetUserAlreadyUsedUserName() throws Exception {
        //GIVEN
        UserRequest userRequest = new UserRequest("test1", "12345",
                new UserInfo(
                        "123@mail.com",
                        "Max",
                        "Mustermann",
                        "musterstraße 1",
                        "Musterstadt 60599",
                        "Deutschland",
                        "Musterfirma"),
                new TaxInfo(
                        "123456789",
                        54000,
                        1,
                        "Finanzamt Köln",
                        null,
                        null));
        AccountUser user = new AccountUser("1", "test1", "12345",
                new UserInfo(
                        "123@mail.com",
                        "Max",
                        "Mustermann",
                        "musterstraße 1",
                        "Musterstadt 60599",
                        "Deutschland",
                        "Musterfirma"),
                new TaxInfo(
                        "123456789",
                        54000,
                        1,
                        "Finanzamt Köln",
                        null,
                        null));
        userRepo.save(user);

        //WHEN
        MvcResult result = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json().build().writeValueAsString(userRequest)))
                //THEN
                .andExpect(status().isBadRequest()).andReturn();
        assertEquals(400, result.getResponse().getStatus());


    }

    @Test
    void testUpdateUserWithWrongPassword() throws Exception {
        //GIVEN
        UserRequest userRequest = new UserRequest("MAx", "12345",
                new UserInfo(
                        "123@mail.com",
                        "Max",
                        "Mustermann",
                        "musterstraße 1",
                        "Musterstadt 60599",
                        "Deutschland",
                        "Musterfirma"),
                new TaxInfo(
                        "123456789",
                        54000,
                        1,
                        "Finanzamt Köln",
                        null,
                        null));
        AccountUser user = new AccountUser("6", "MAx", "192439874",
                new UserInfo(
                        "123@mail.com",
                        "Max",
                        "Mustermann",
                        "musterstraße 1",
                        "Musterstadt 60599",
                        "Deutschland",
                        "Musterfirma"),
                new TaxInfo(
                        "123456789",
                        54000,
                        1,
                        "Finanzamt Köln",
                        null,
                        null));

        userRepo.save(user);

        //WHEN
        MvcResult result = mockMvc.perform(put("/api/user/6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json().build().writeValueAsString(userRequest)))
                //THEN
                .andExpect(status().isBadRequest()).andReturn();
        assertEquals(400, result.getResponse().getStatus());

    }

    @Test
    void testUpdateUserWithWrongId() throws Exception {
        //GIVEN
        UserRequest userRequest = new UserRequest("test1", "12345",
                new UserInfo(
                        "123@mail.com",
                        "Max",
                        "Mustermann",
                        "musterstraße 1",
                        "Musterstadt 60599",
                        "Deutschland",
                        "Musterfirma"),
                new TaxInfo(
                        "123456789",
                        54000,
                        1,
                        "Finanzamt Köln",
                        null,
                        null));
        AccountUser user = new AccountUser("2", "test1", "12345",
                new UserInfo(
                        "123@mail.com",
                        "Max",
                        "Mustermann",
                        "musterstraße 1",
                        "Musterstadt 60599",
                        "Deutschland",
                        "Musterfirma"),
                new TaxInfo(
                        "123456789",
                        54000,
                        1,
                        "Finanzamt Köln",
                        null,
                        null));

        userRepo.save(user);

        //WHEN
        MvcResult result = mockMvc.perform(put("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json().build().writeValueAsString(userRequest)))
                //THEN
                .andExpect(status().isNotFound()).andReturn();
        assertEquals(404, result.getResponse().getStatus());

    }
}



