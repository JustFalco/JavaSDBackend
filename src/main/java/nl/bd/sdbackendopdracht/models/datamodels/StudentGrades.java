package nl.bd.sdbackendopdracht.models.datamodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "student_grades")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
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
    private Long studentMarkId;
    private LocalDateTime insertionDate;
    private String description;
    private float grade;
    private int weight;
    private LocalDate testDate;

    @JsonIgnore
    @ManyToOne
    private User markBelongsToStudent;
    @JsonIgnore
    @OneToOne
    private User submittedByTeacher;
    @JsonIgnore
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StudentGrades that = (StudentGrades) o;
        return studentMarkId != null && Objects.equals(studentMarkId, that.studentMarkId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
