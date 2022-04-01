package nl.bd.sdbackendopdracht.models.datamodels;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "Courses")
public class Course {

    @Id
    @SequenceGenerator(
            name = "course_id_generator",
            sequenceName = "course_id_generator"
    )
    @GeneratedValue(
            generator = "course_id_generator",
            strategy = GenerationType.SEQUENCE
    )
    private Long courseId;
    private String courseName;
    private String courseDescription;

    @ManyToOne
    private School belongsToSchool;

    @ManyToOne
    private Teacher teacherGivesCourse;
}
