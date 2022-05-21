package nl.bd.sdbackendopdracht.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bd.sdbackendopdracht.models.requestmodels.AbsenceRegistrationRequest;
import nl.bd.sdbackendopdracht.models.requestmodels.UserRegistrationRequest;
import nl.bd.sdbackendopdracht.security.enums.AbsenceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "Admin", password = "SuperStrongP@ssword123", authorities = {"DEVELOPER"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AbsenceControllerTest {

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
    void submitAbsence() throws Exception {
        //Create student
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

        //Submit absence with no errors
        AbsenceRegistrationRequest request2 = new AbsenceRegistrationRequest(
                2L,
                AbsenceTypes.ABSENT,
                "Student was niet aanwezig"
        );

        String jsonBody2 = objectMapper.writeValueAsString(request2);

        mockMvc.perform(post("/api/v1/administrator/absence/submit").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.absenceDescription").value("Student was niet aanwezig"))
                .andDo(print());

        //Submit absence with non existing student
        AbsenceRegistrationRequest request3 = new AbsenceRegistrationRequest(
                21235L,
                AbsenceTypes.ABSENT,
                "Student was niet aanwezig"
        );

        String jsonBody3 = objectMapper.writeValueAsString(request3);

        mockMvc.perform(post("/api/v1/administrator/absence/submit").contentType(APPLICATION_JSON_UTF8).content(jsonBody3))
                .andExpect(status().isNotFound())
                .andDo(print());

        //Submit absence with non student
        AbsenceRegistrationRequest request4 = new AbsenceRegistrationRequest(
                1L, //Admin id
                AbsenceTypes.ABSENT,
                "Student was niet aanwezig"
        );

        String jsonBody4 = objectMapper.writeValueAsString(request4);

        mockMvc.perform(post("/api/v1/administrator/absence/submit").contentType(APPLICATION_JSON_UTF8).content(jsonBody4))
                .andExpect(status().is4xxClientError())
                .andDo(print());

    }

    @Test
    void getAbsenceData() throws Exception {
        //Create student
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

        //Submit absence with no errors
        AbsenceRegistrationRequest request2 = new AbsenceRegistrationRequest(
                2L,
                AbsenceTypes.ABSENT,
                "Student was niet aanwezig"
        );

        String jsonBody2 = objectMapper.writeValueAsString(request2);

        mockMvc.perform(post("/api/v1/administrator/absence/submit").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.absenceDescription").value("Student was niet aanwezig"))
                .andDo(print());

        //Get absence data with no errors
        mockMvc.perform(get("/api/v1/absence/absenceId=1"))
                .andExpect(jsonPath("$.absenceDescription").value("Student was niet aanwezig"))
                .andExpect(jsonPath("$.absenceType").value("ABSENT"))
                .andExpect(status().isOk())
                .andDo(print());

        //Get absence data from invalid id
        mockMvc.perform(get("/api/v1/absence/absenceId=1234234"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void changeAbsence() {
    }

    @Test
    void deleteAbsence() {
    }

    @Test
    void getAbsenceFromStudent() {
    }
}