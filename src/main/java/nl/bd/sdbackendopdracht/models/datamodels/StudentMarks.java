package nl.bd.sdbackendopdracht.models.datamodels;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_marks")
public class StudentMarks {
    @Id
    @SequenceGenerator(
            name = "mark_id_generator",
            sequenceName = "mark_id_generator"
    )
    @GeneratedValue(
            generator = "mark_id_generator",
            strategy = GenerationType.SEQUENCE
    )
    private int Id;
    private int markBelongsToStudentId;
    private LocalDateTime insertionDate;
    private String description;
    private float cijfer;
    private int weight;
    private int submittedByTeacherId;
    private int taskId;
    private LocalDate testDate;

    public StudentMarks() {
    }

    public StudentMarks(int id, int markBelongsToStudentId, LocalDateTime insertionDate, String description, float cijfer, int weight, int submittedByTeacherId, int taskId, LocalDate testDate) {
        Id = id;
        this.markBelongsToStudentId = markBelongsToStudentId;
        this.insertionDate = insertionDate;
        this.description = description;
        this.cijfer = cijfer;
        this.weight = weight;
        this.submittedByTeacherId = submittedByTeacherId;
        this.taskId = taskId;
        this.testDate = testDate;
    }

    public StudentMarks(int markBelongsToStudentId, LocalDateTime insertionDate, String description, float cijfer, int weight, int submittedByTeacherId, int taskId, LocalDate testDate) {
        this.markBelongsToStudentId = markBelongsToStudentId;
        this.insertionDate = insertionDate;
        this.description = description;
        this.cijfer = cijfer;
        this.weight = weight;
        this.submittedByTeacherId = submittedByTeacherId;
        this.taskId = taskId;
        this.testDate = testDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getMarkBelongsToStudentId() {
        return markBelongsToStudentId;
    }

    public void setMarkBelongsToStudentId(int markBelongsToStudentId) {
        this.markBelongsToStudentId = markBelongsToStudentId;
    }

    public LocalDateTime getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(LocalDateTime insertionDate) {
        this.insertionDate = insertionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getCijfer() {
        return cijfer;
    }

    public void setCijfer(float cijfer) {
        this.cijfer = cijfer;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSubmittedByTeacherId() {
        return submittedByTeacherId;
    }

    public void setSubmittedByTeacherId(int submittedByTeacherId) {
        this.submittedByTeacherId = submittedByTeacherId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    @Override
    public String toString() {
        return "StudentMarks{" +
                "Id=" + Id +
                ", markBelongsToStudentId=" + markBelongsToStudentId +
                ", insertionDate=" + insertionDate +
                ", description='" + description + '\'' +
                ", cijfer=" + cijfer +
                ", weight=" + weight +
                ", submittedByTeacherId=" + submittedByTeacherId +
                ", taskId=" + taskId +
                ", testDate=" + testDate +
                '}';
    }
}
