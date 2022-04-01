package nl.bd.sdbackendopdracht.models.datamodels;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tasks")
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
    private int Id;
    private String taskName;
    private String taksDescription;
    //TODO nieuwe feature waarmee je bijlagen toe kan voegen
    private LocalDateTime taksDeadline;
    private LocalDateTime timeOfTaskPublication;

    public Task() {
    }

    public Task(int id, String taskName, String taksDescription, LocalDateTime taksDeadline, LocalDateTime timeOfTaskPublication) {
        Id = id;
        this.taskName = taskName;
        this.taksDescription = taksDescription;
        this.taksDeadline = taksDeadline;
        this.timeOfTaskPublication = timeOfTaskPublication;
    }

    public Task(String taskName, String taksDescription, LocalDateTime taksDeadline, LocalDateTime timeOfTaskPublication) {
        this.taskName = taskName;
        this.taksDescription = taksDescription;
        this.taksDeadline = taksDeadline;
        this.timeOfTaskPublication = timeOfTaskPublication;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaksDescription() {
        return taksDescription;
    }

    public void setTaksDescription(String taksDescription) {
        this.taksDescription = taksDescription;
    }

    public LocalDateTime getTaksDeadline() {
        return taksDeadline;
    }

    public void setTaksDeadline(LocalDateTime taksDeadline) {
        this.taksDeadline = taksDeadline;
    }

    public LocalDateTime getTimeOfTaskPublication() {
        return timeOfTaskPublication;
    }

    public void setTimeOfTaskPublication(LocalDateTime timeOfTaskPublication) {
        this.timeOfTaskPublication = timeOfTaskPublication;
    }

    @Override
    public String toString() {
        return "Task{" +
                "Id=" + Id +
                ", taskName='" + taskName + '\'' +
                ", taksDescription='" + taksDescription + '\'' +
                ", taksDeadline=" + taksDeadline +
                ", timeOfTaskPublication=" + timeOfTaskPublication +
                '}';
    }
}
