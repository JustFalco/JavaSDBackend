package nl.bd.sdbackendopdracht.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.parser.JSONParser;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.UserRegistrationRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "Admin", password = "SuperStrongP@ssword123", authorities = {"DEVELOPER"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }


    @Test
    void getPersonalDetails() throws Exception {
        mockMvc.perform(get("/api/v1/user/get_details"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.email").value("Admin"))
                .andExpect(status().isOk());
    }

    //Get current user with wrong username
    @Test
    @WithMockUser(username = "Admin1", password = "SuperStrongP@ssword123", authorities = {"DEVELOPER"})
    void getPersonalDetailsFail() throws Exception {
        mockMvc.perform(get("/api/v1/user/get_details"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getUser() throws Exception {
        //Register user with no errors
        extracted();


        //Test if we get user back that was submitted to database
        mockMvc.perform(get("/api/v1/user/get_details/user=2"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.email").value("nick@schuit.nl"))
                .andExpect(jsonPath("$.firstName").value("Nick"))
                .andExpect(status().isOk());

        //Test with wrong id
        mockMvc.perform(get("/api/v1/user/get_details/user=237846"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private void extracted() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest(
                "Nick",
                null,
                "Schuit",
                "nick@schuit.nl",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/v1/administrator/registration/register_student").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Nick"))
                .andExpect(jsonPath("$.email").value("nick@schuit.nl"))
                .andReturn();
    }

    @Test
    void changeUser() throws Exception {
        //Register user with no errors
        extracted();

        //Change users first and last name with email nick@schuit.nl
        UserRegistrationRequest request2 = new UserRegistrationRequest(
                "Falco",
                null,
                "Wolkorte",
                "nick@schuit.nl",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody2 = objectMapper.writeValueAsString(request2);
        mockMvc.perform(put("/api/v1/user/change/email=nick@schuit.nl").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Falco"))
                .andExpect(jsonPath("$.lastName").value("Wolkorte"))
                .andDo(print());

        //Change user with duplicate email (in url)
        mockMvc.perform(put("/api/v1/user/change/email=nick2@schuit.nl").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andExpect(status().isNotFound())
                .andDo(print());

        //Change user with illegal email (in url)
        mockMvc.perform(put("/api/v1/user/change/email=nick").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getByEmail() throws Exception {
        //Register user with no errors
        extracted();

        //Try to get user from database with his email
        mockMvc.perform(get("/api/v1/user/get_details/email=nick@schuit.nl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("nick@schuit.nl"))
                .andExpect(jsonPath("$.lastName").value("Schuit"))
                .andDo(print());

        //Try to get user from database with wrong email
        mockMvc.perform(get("/api/v1/user/get_details/email=nick1@schuit.nl"))
                .andDo(print())
                .andExpect(status().isNotFound());

        //Try to get user from database with illegal email
        mockMvc.perform(get("/api/v1/user/get_details/email=SELECT s FROM User s WHERE s.firstName = 'Nick'"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }


}