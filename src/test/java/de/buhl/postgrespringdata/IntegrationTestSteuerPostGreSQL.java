package de.buhl.postgrespringdata;

import de.buhl.postgrespringdata.model.entity.AccountUser;
import de.buhl.postgrespringdata.model.entity.submodel.TaxInfo;
import de.buhl.postgrespringdata.model.entity.submodel.UserInfo;
import de.buhl.postgrespringdata.repository.UserRepo;
import de.buhl.postgrespringdata.util.IdService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTestSteuerPostGreSQL {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IdService idService;

    @Autowired
    private UserRepo userRepo;

    private static MockWebServer mockWebServer;

    @BeforeAll
    public static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

    }

    @DynamicPropertySource
    public static void configureUrl(DynamicPropertyRegistry registry) {
        registry.add("jdbc:postgresql://localhost:5432/postgres",
                () -> mockWebServer.url("/").toString());

    }

    @AfterAll
    public static void cleanup() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DirtiesContext
    public void getAllUser() throws Exception {
        //GIVEN
        userRepo.save(new AccountUser("1", "test", "test",
                new UserInfo("test", "test", "test", "test", "test", "test", "test")
                , new TaxInfo("1", 12345, null, null)));
        userRepo.save(new AccountUser("2", "test", "test",
                new UserInfo("test", "test", "test", "test", "test", "test", "test"),
                new TaxInfo("2", 12345, null, null)));


        mockWebServer.enqueue(new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody("""{
                             
                             }"""));
        //WHEN

        MvcResult mvcResult = mockMvc.perform(get("/api/users"))
                .andExpect(status().isAccepted())
                .andExpect(content().json("""
                        [
                        {
                        "id":"1",
                "username":"test",
                "password":"test",
                "userInfo":{
                            "email":"test",
                    "firstName":"test",
                    "lastName":"test",
                    "street":"test",
                    "houseNumber":"test",
                    "zipCode":"test",
                    "city":"test"
        },
        "taxInfo":{
            "id":"1",
                    "taxNumber":12345,
                    "taxOffice":null,
                    "taxClass":null
        }
                },
        {
            "id":"2",
                "username":"test",
                "password":"test",
                "userInfo":{
            "id":"test",
                    "firstName":"test",
                    "lastName":"test",
                    "street":"test",
                    "houseNumber":"test",
                    "zipCode":"test",
                    "city":"test"
        },
            "taxInfo":{
            "id":"2",
                    "taxNumber":12345,
                    "taxOffice":null,
                    "taxClass":null
        }
        }]"""))
                .andReturn();
        //THEN
        assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

}
