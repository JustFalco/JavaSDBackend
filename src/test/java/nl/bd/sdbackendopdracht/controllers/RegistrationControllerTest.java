package nl.bd.sdbackendopdracht.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bd.sdbackendopdracht.models.requestmodels.SchoolRegistrationRequest;
import nl.bd.sdbackendopdracht.models.requestmodels.UserRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "Admin", password = "SuperStrongP@ssword123", authorities = {"DEVELOPER"})
class RegistrationControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void beforEach() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void registerStudent() throws Exception {
        //User with no errors
        UserRegistrationRequest request = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "falco@wolkorte.nl",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/administrator/registration/register_student").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk());

        //User request with empty email

        UserRegistrationRequest request2 = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody2 = objectMapper.writeValueAsString(request2);

        mockMvc.perform(post("/api/v1/administrator/registration/register_student").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        //User request with empty password
        UserRegistrationRequest request3 = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "falco2@wolkorte.nl",
                null,
                "",
                1,
                true
        );

        String jsonBody3 = objectMapper.writeValueAsString(request3);

        mockMvc.perform(post("/api/v1/administrator/registration/register_student").contentType(APPLICATION_JSON_UTF8).content(jsonBody3))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        //Duplicate user
        UserRegistrationRequest request4 = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "falco@wolkorte.nl",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody4 = objectMapper.writeValueAsString(request4);

        mockMvc.perform(post("/api/v1/administrator/registration/register_student").contentType(APPLICATION_JSON_UTF8).content(jsonBody4))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        // Check if the result has a size of 1
        mockMvc.perform(get("/api/v1/user/get_details/email=falco@wolkorte.nl"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.length()", ))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void registerAdministrator() throws Exception {
        //User with no errors
        UserRegistrationRequest request = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "falco3@wolkorte.nl",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/administrator/registration/register_administrator").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk());

        //Duplicate user
        mockMvc.perform(post("/api/v1/administrator/registration/register_administrator").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        //User with no email
        UserRegistrationRequest request2 = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody2 = objectMapper.writeValueAsString(request2);

        mockMvc.perform(post("/api/v1/administrator/registration/register_administrator").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        //User with no password
        UserRegistrationRequest request3 = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "falco2@wolkorte.nl",
                null,
                "",
                1,
                true
        );

        String jsonBody3 = objectMapper.writeValueAsString(request3);

        mockMvc.perform(post("/api/v1/administrator/registration/register_administrator").contentType(APPLICATION_JSON_UTF8).content(jsonBody3))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        // Check if the result has a size of 1
        mockMvc.perform(get("/api/v1/user/get_details/email=falco@wolkorte.nl"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$").value(RoleEnums.ADMINISTRATOR.name()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void registerTeacher() throws Exception {
//User with no errors
        UserRegistrationRequest request = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "falco4@wolkorte.nl",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/administrator/registration/register_teacher").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk());

        //Duplicate user
        mockMvc.perform(post("/api/v1/administrator/registration/register_teacher").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        //User with no email
        UserRegistrationRequest request2 = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody2 = objectMapper.writeValueAsString(request2);

        mockMvc.perform(post("/api/v1/administrator/registration/register_teacher").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        //User with no password
        UserRegistrationRequest request3 = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "falco6@wolkorte.nl",
                null,
                "",
                1,
                true
        );

        String jsonBody3 = objectMapper.writeValueAsString(request3);

        mockMvc.perform(post("/api/v1/administrator/registration/register_teacher").contentType(APPLICATION_JSON_UTF8).content(jsonBody3))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        // Check if the result has a size of 1
        mockMvc.perform(get("/api/v1/user/get_details/email=falco4@wolkorte.nl"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$").value(RoleEnums.ADMINISTRATOR.name()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void registerSchool() throws Exception {
        //School with no errors
        SchoolRegistrationRequest request = new SchoolRegistrationRequest(
                "JenaXL",
                "jenaxl@gmail.com"
        );

        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/registration/register_school").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk());

        //Duplicate email
        SchoolRegistrationRequest request2 = new SchoolRegistrationRequest(
                "JenaXL2",
                "jenaxl@gmail.com"
        );

        String jsonBody2 = objectMapper.writeValueAsString(request2);

        mockMvc.perform(post("/api/v1/registration/register_school").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        //Duplicate schoolname
        SchoolRegistrationRequest request3 = new SchoolRegistrationRequest(
                "JenaXL",
                "jenaxl2@gmail.com"
        );

        String jsonBody3 = objectMapper.writeValueAsString(request3);

        mockMvc.perform(post("/api/v1/registration/register_school").contentType(APPLICATION_JSON_UTF8).content(jsonBody3))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        //illegal email
        SchoolRegistrationRequest request4 = new SchoolRegistrationRequest(
                "JenaXL3",
                "jenaxl"
        );

        String jsonBody4 = objectMapper.writeValueAsString(request4);

        mockMvc.perform(post("/api/v1/registration/register_school").contentType(APPLICATION_JSON_UTF8).content(jsonBody4))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        //illegal name
        SchoolRegistrationRequest request5 = new SchoolRegistrationRequest(
                "JenaXL3()><##@",
                "jenaxlzwolle@gmail.com"
        );

        String jsonBody5 = objectMapper.writeValueAsString(request5);

        mockMvc.perform(post("/api/v1/registration/register_school").contentType(APPLICATION_JSON_UTF8).content(jsonBody5))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

}