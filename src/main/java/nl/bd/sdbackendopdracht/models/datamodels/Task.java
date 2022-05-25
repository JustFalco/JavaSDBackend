package nl.bd.sdbackendopdracht.models.datamodels;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @ManyToMany
    @JoinTable(
            name = "user_has_task",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "taskId"))
    @Builder.Default
    private Set<User> taskHasUsers = new HashSet<>();

    @ManyToOne
    private User taskGivenByTeacher;

    public void addUser(User user) {
        this.taskHasUsers.add(user);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "taskId = " + taskId + ", " +
                "taskName = " + taskName + ", " +
                "taksDescription = " + taksDescription + ", " +
                "taksDeadline = " + taksDeadline + ", " +
                "timeOfTaskPublication = " + timeOfTaskPublication + ", " +
                "taskFinished = " + taskFinished + ", " +
                "taskGivenByTeacher = " + taskGivenByTeacher + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Task task = (Task) o;
        return taskId != null && Objects.equals(taskId, task.taskId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
