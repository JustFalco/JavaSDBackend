package nl.bd.sdbackendopdracht.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "Admin", password = "SuperStrongP@ssword123", authorities = {"DEVELOPER"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CourseControllerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void createCourse() {
    }

    @Test
    void changeCourse() {
    }

    @Test
    void addStudentToCourse() {
    }

    @Test
    void removeStudent() {
    }

    @Test
    void addMultipleStudentsToCourse() {
    }

    @Test
    void removeCourse() {
    }

    @Test
    void getCourseDetails() {
    }
}