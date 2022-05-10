package nl.bd.sdbackendopdracht.services;

import nl.bd.sdbackendopdracht.models.datamodels.Task;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.models.requestmodels.TaskRegistrationRequest;
import nl.bd.sdbackendopdracht.repositories.CourseRepository;
import nl.bd.sdbackendopdracht.repositories.TaskRepository;
import nl.bd.sdbackendopdracht.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TasksServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CourseService courseService;
    @Mock
    private UserService userService;

    private AutoCloseable autoCloseable;
    private TasksService tasksService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        tasksService = new TasksService(userRepository, taskRepository, courseRepository, courseService, userService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getTask() {
        //arange
        Long id = 1L;
        Task givenTask = Task.builder()
                .taskId(id)
                .taskName("Test task")
                .taksDescription("Test task object")
                .taksDeadline(LocalDateTime.MAX)
                .build();

        when(taskRepository.save(any(Task.class))).thenReturn(givenTask);

        //act
        taskRepository.save(givenTask);
        tasksService.getTask(id);

        //assert
        verify(taskRepository).getById(id);
    }


    @Test
    void createTask() {
        //arange
        String mail = "falco@wolkorte.nl";

        TaskRegistrationRequest request = new TaskRegistrationRequest("Test task", "test task object", LocalDateTime.MAX);
        User personalUserDetails = userService.getPersonalUserDetails(mail);
        Task createdTask = Task.builder()
                .taskName(request.getTaskName())
                .taksDescription(request.getTaskDescription())
                .taksDeadline(request.getTaskDeadline())
//                .timeOfTaskPublication(LocalDateTime.now())
                .taskFinished(false)
                .taskGivenByTeacher(personalUserDetails)
                .build();

        when(taskRepository.save(any(Task.class))).thenReturn(createdTask);

        //act
        Task expectedTask = tasksService.createTask(request, mail);

        //assert
        assertThat(expectedTask).isEqualTo(createdTask);
    }
}