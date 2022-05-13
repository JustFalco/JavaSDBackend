package nl.bd.sdbackendopdracht.models.datamodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Task {
    @Id
    @SequenceGenerator(
            name = "task_id_generator",
            sequenceName = "task_id_generator"
    )
    @GeneratedValue(
            generator = "task_id_generator",
            strategy = GenerationType.SEQUENCE
    )
    private Long taskId;
    private String taskName;
    private String taksDescription;

    private LocalDateTime taksDeadline;
    private LocalDateTime timeOfTaskPublication;
    private Boolean taskFinished;
    //TODO nieuwe feature waarmee je bijlagen toe kan voegen

    @ManyToMany
    @JoinTable(
            name = "user_has_task",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "taskId"))
    @Builder.Default private Set<User> taskHasUsers = new HashSet<>();

    @ManyToOne
    private User taskGivenByTeacher;

    public void addUser(User user) {
        this.taskHasUsers.add(user);
    }
}
