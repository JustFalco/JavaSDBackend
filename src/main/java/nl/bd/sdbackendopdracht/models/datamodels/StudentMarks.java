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
    private int studentMarkId;
    private LocalDateTime insertionDate;
    private String description;
    private float cijfer;
    private int weight;
    private LocalDate testDate;

    @ManyToOne
    private User markBelongsToStudent;
    @OneToOne
    private User submittedByTeacher;

    @OneToOne
    private Task markBelongsToTask;

    public StudentMarks(LocalDateTime insertionDate, String description, float cijfer, int weight, LocalDate testDate, User markBelongsToStudent, User submittedByTeacher) {
        this.insertionDate = insertionDate;
        this.description = description;
        this.cijfer = cijfer;
        this.weight = weight;
        this.testDate = testDate;
        this.markBelongsToStudent = markBelongsToStudent;
        this.submittedByTeacher = submittedByTeacher;
    }
}
