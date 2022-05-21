package nl.bd.sdbackendopdracht.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "Admin", password = "SuperStrongP@ssword123", authorities = {"DEVELOPER"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TaskControllerTest {

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
    void createTaskForCourse() {
    }

    @Test
    void createTask() {
    }

    @Test
    void addFileToTask() {
    }

    @Test
    void getFile() {
    }

    @Test
    void getAllTaskfilesFromTask() {
    }

    @Test
    void changeTask() {
    }

    @Test
    void getTaskDetails() {
    }

    @Test
    void getTasksFromStudent() {
    }

    @Test
    void addStudentToTask() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void testDeleteTask() {
    }
}