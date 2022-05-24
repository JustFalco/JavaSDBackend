package nl.bd.sdbackendopdracht.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bd.sdbackendopdracht.models.requestmodels.CourseRegistrationRequest;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "Admin", password = "SuperStrongP@ssword123", authorities = {"DEVELOPER"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CourseControllerTest {

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
    void createCourse() throws Exception {
        //Create course with no errors
        CourseRegistrationRequest request = new CourseRegistrationRequest(
                "Wiskunde B",
                "Wiskunde B voor vwo 6",
                1L
        );
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/administrator/course/create").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Wiskunde B"));

        //Create course with non existing teacher
        CourseRegistrationRequest request2 = new CourseRegistrationRequest(
                "Wiskunde B",
                "Wiskunde B voor vwo 6",
                12352124L
        );
        String jsonBody2 = objectMapper.writeValueAsString(request2);

        mockMvc.perform(post("/api/v1/administrator/course/create").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        //Create course with student user as teacher
        UserRegistrationRequest request3 = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "falco@wolkorte.nl",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody3 = objectMapper.writeValueAsString(request3);

        mockMvc.perform(post("/api/v1/administrator/registration/register_student").contentType(APPLICATION_JSON_UTF8).content(jsonBody3))
                .andDo(print())
                .andExpect(status().isOk());

        CourseRegistrationRequest request4 = new CourseRegistrationRequest(
                "Wiskunde B",
                "Wiskunde B voor vwo 6",
                2L
        );
        String jsonBody4 = objectMapper.writeValueAsString(request4);

        mockMvc.perform(post("/api/v1/administrator/course/create").contentType(APPLICATION_JSON_UTF8).content(jsonBody4))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void changeCourse() throws Exception {
        //Create course with no errors
        CourseRegistrationRequest request = new CourseRegistrationRequest(
                "Wiskunde B",
                "Wiskunde B voor vwo 6",
                1L
        );
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/administrator/course/create").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Wiskunde B"));

        //Change course with no errors
        CourseRegistrationRequest request2 = new CourseRegistrationRequest(
                "Wiskunde C",
                "Wiskunde C voor HAVO 5",
                1L
        );
        String jsonBody2 = objectMapper.writeValueAsString(request2);

        mockMvc.perform(put("/api/v1/administrator/course/change/course=1").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Wiskunde C"))
                .andExpect(jsonPath("$.courseDescription").value("Wiskunde C voor HAVO 5"));

        //Change course with not existing teacher
        CourseRegistrationRequest request3 = new CourseRegistrationRequest(
                "Wiskunde C",
                "Wiskunde C voor HAVO 5",
                123523523L
        );
        String jsonBody3 = objectMapper.writeValueAsString(request3);

        mockMvc.perform(put("/api/v1/administrator/course/change/course=1").contentType(APPLICATION_JSON_UTF8).content(jsonBody3))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        //Change course with student as teacher
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
                .andExpect(status().isOk());

        CourseRegistrationRequest request5 = new CourseRegistrationRequest(
                "Wiskunde C",
                "Wiskunde C voor HAVO 5",
                2L
        );
        String jsonBody5 = objectMapper.writeValueAsString(request5);

        mockMvc.perform(put("/api/v1/administrator/course/change/course=1").contentType(APPLICATION_JSON_UTF8).content(jsonBody5))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void addStudentToCourse() throws Exception {
        //Create student and course
        CourseRegistrationRequest request = new CourseRegistrationRequest(
                "Wiskunde B",
                "Wiskunde B voor vwo 6",
                1L
        );
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/administrator/course/create").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Wiskunde B"));

        UserRegistrationRequest request2 = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "falco@wolkorte.nl",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody2 = objectMapper.writeValueAsString(request2);

        mockMvc.perform(post("/api/v1/administrator/registration/register_student").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andDo(print())
                .andExpect(status().isOk());

        //Add student with no errors
        mockMvc.perform(post("/api/v1/administrator/course/add_student/student=2&course=1"))
                .andExpect(status().isOk())
                .andDo(print());

        //Add non existing student
        mockMvc.perform(post("/api/v1/administrator/course/add_student/student=23452&course=1"))
                .andExpect(status().isNotFound())
                .andDo(print());

        //Add student to non existing course
        mockMvc.perform(post("/api/v1/administrator/course/add_student/student=2&course=1423523"))
                .andExpect(status().isNotFound())
                .andDo(print());

        //TODO Add administrator to course
    }

    @Test
    void removeStudent() throws Exception {
        //First create course and add student to course
        //Create student and course
        CourseRegistrationRequest request = new CourseRegistrationRequest(
                "Wiskunde B",
                "Wiskunde B voor vwo 6",
                1L
        );
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/administrator/course/create").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Wiskunde B"));

        UserRegistrationRequest request2 = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "falco@wolkorte.nl",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody2 = objectMapper.writeValueAsString(request2);

        mockMvc.perform(post("/api/v1/administrator/registration/register_student").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andDo(print())
                .andExpect(status().isOk());

        UserRegistrationRequest request3 = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "nick@schuit.nl",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody3 = objectMapper.writeValueAsString(request3);

        mockMvc.perform(post("/api/v1/administrator/registration/register_student").contentType(APPLICATION_JSON_UTF8).content(jsonBody3))
                .andDo(print())
                .andExpect(status().isOk());

        //Add student with no errors
        mockMvc.perform(post("/api/v1/administrator/course/add_student/student=2&course=1"))
                .andExpect(status().isOk())
                .andDo(print());

        //Remove student from course
        mockMvc.perform(delete("/api/v1/administrator/course/delete_student/student=2&course=1"))
                .andExpect(status().isOk());

        //Try remove student from nonexisting course
        mockMvc.perform(delete("/api/v1/administrator/course/delete_student/student=2&course=1234234"))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        //Try remove nonexisting student
        mockMvc.perform(delete("/api/v1/administrator/course/delete_student/student=235234&course=1"))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        //Try to remove non student
        mockMvc.perform(delete("/api/v1/administrator/course/delete_student/student=1&course=1"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void addMultipleStudentsToCourse() throws Exception {
        //First create multiple students
        UserRegistrationRequest request = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "nick@schuit.nl",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/administrator/registration/register_student").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk());

        UserRegistrationRequest request2 = new UserRegistrationRequest(
                "Faclo",
                null,
                "Wolkorte",
                "fa@wo.nl",
                null,
                "F@lcoW0lkorte",
                1,
                true
        );

        String jsonBody2 = objectMapper.writeValueAsString(request2);

        mockMvc.perform(post("/api/v1/administrator/registration/register_student").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andDo(print())
                .andExpect(status().isOk());

        List<Long> idList = new ArrayList<>();
        idList.add(2L);
        idList.add(3L);

        String jsonBody3 = objectMapper.writeValueAsString(idList);

        //Try and write students to non exsisting course
        mockMvc.perform(post("/api/v1/administrator/course/add_students/course=13245").contentType(APPLICATION_JSON_UTF8).content(jsonBody3))
                .andDo(print())
                .andExpect(status().isNotFound());

        //Create course
        CourseRegistrationRequest request4 = new CourseRegistrationRequest(
                "Wiskunde B",
                "Wiskunde B voor vwo 6",
                1L
        );
        String jsonBody4 = objectMapper.writeValueAsString(request4);

        mockMvc.perform(post("/api/v1/administrator/course/create").contentType(APPLICATION_JSON_UTF8).content(jsonBody4))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Wiskunde B"));

        //Try and write students to existing course
        mockMvc.perform(post("/api/v1/administrator/course/add_students/course=1").contentType(APPLICATION_JSON_UTF8).content(jsonBody3))
                .andDo(print())
                .andExpect(status().isOk());

        //try and write non student to course
        idList.add(1L); //The admin id

        String jsonBody5 = objectMapper.writeValueAsString(idList);

        mockMvc.perform(post("/api/v1/administrator/course/add_students/course=1").contentType(APPLICATION_JSON_UTF8).content(jsonBody5))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        //try and write non existing student to course
        idList.add(12345L);

        String jsonBody6 = objectMapper.writeValueAsString(idList);
        mockMvc.perform(post("/api/v1/administrator/course/add_students/course=1").contentType(APPLICATION_JSON_UTF8).content(jsonBody6))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void removeCourse() throws Exception {
        //Create course
        CourseRegistrationRequest request = new CourseRegistrationRequest(
                "Wiskunde B",
                "Wiskunde B voor vwo 6",
                1L
        );
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/administrator/course/create").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Wiskunde B"));

        //Remove created course
        mockMvc.perform(delete("/api/v1/administrator/course/remove/course=1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Course with id: 1 has succesfully been removed!"))
                .andDo(print());

        //Try to remove non existing course
        mockMvc.perform(delete("/api/v1/administrator/course/remove/course=1235235"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getCourseDetails() throws Exception {
        //Create course
        CourseRegistrationRequest request = new CourseRegistrationRequest(
                "Wiskunde B",
                "Wiskunde B voor vwo 6",
                1L
        );
        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/administrator/course/create").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Wiskunde B"));

        //Get course details from existing course
        mockMvc.perform(get("/api/v1/user/course/get_details/course=1"))
                .andExpect(jsonPath("$.courseName").value("Wiskunde B"))
                .andExpect(jsonPath("$.courseDescription").value("Wiskunde B voor vwo 6"))
                .andExpect(status().isOk())
                .andDo(print());

        //Get course details from non existing course
        mockMvc.perform(get("/api/v1/user/course/get_details/course=1234523"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}