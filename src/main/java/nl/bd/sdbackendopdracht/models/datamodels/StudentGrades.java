package nl.bd.sdbackendopdracht.models.datamodels;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_marks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StudentGrades {
    @Id
    @SequenceGenerator(
            name = "mark_id_generator",
            sequenceName = "mark_id_generator"
    )
    @GeneratedValue(
            generator = "mark_id_generator",
            strategy = GenerationType.SEQUENCE
    )
    private int studentMarkId;
    private LocalDateTime insertionDate;
    private String description;
    private float grade;
    private int weight;
    private LocalDate testDate;

    @ManyToOne
    private User markBelongsToStudent;
    @OneToOne
    private User submittedByTeacher;

    @OneToOne
    private Task markBelongsToTask;

    public StudentGrades(LocalDateTime insertionDate, String description, float grade, int weight, LocalDate testDate, User markBelongsToStudent, User submittedByTeacher) {
        this.insertionDate = insertionDate;
        this.description = description;
        this.grade = grade;
        this.weight = weight;
        this.testDate = testDate;
        this.markBelongsToStudent = markBelongsToStudent;
        this.submittedByTeacher = submittedByTeacher;
    }
}
