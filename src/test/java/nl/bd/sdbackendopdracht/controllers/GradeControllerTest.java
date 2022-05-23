package nl.bd.sdbackendopdracht.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bd.sdbackendopdracht.models.requestmodels.GradeRegistrationRequest;
import nl.bd.sdbackendopdracht.models.requestmodels.UserRegistrationRequest;
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
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "Admin", password = "SuperStrongP@ssword123", authorities = {"DEVELOPER"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GradeControllerTest {

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
    void getLastFifteenGrades() {
    }

    @Test
    void getGradeOverview() {
    }

    @Test
    void getGrade() {
    }

    @Test
    void submitGrade() throws Exception {

        //Create grade registration request
        String jsonBody2 = createGrade();

        //Submit grade with non existing student id
        mockMvc.perform(post("/api/v1/teacher/grades/submit_grade/student=3245232").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        //Submit grade with wrong student id
        mockMvc.perform(post("/api/v1/teacher/grades/submit_grade/student=-1").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        //Submit grade to non student

        //Test float

        //Test grade

        //Test task id
    }

    @Test
    void changeGrade() {

    }

    @Test
    void deleteGrade() throws Exception {
        createGrade();

        //Delete the created grade
        mockMvc.perform(delete("/api/v1/teacher/grades/delete/grade=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        //Delete non existing grade
        mockMvc.perform(delete("/api/v1/teacher/grades/delete/grade=12345235"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    private String createGrade() throws Exception {
        createUser();
        GradeRegistrationRequest request2 = new GradeRegistrationRequest(
                "Grade for maths test",
                6.8f,
                3,
                null,
                null
        );

        String jsonBody2 = objectMapper.writeValueAsString(request2);


        //Submit grade with no errors
        mockMvc.perform(post("/api/v1/teacher/grades/submit_grade/student=2").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Grade for maths test"))
                .andReturn();
        return jsonBody2;
    }

    private void createUser() throws Exception {
        //Create student
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
}