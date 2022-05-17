package nl.bd.sdbackendopdracht.models.datamodels;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
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
    private User teacherGivesCourse;

//    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "student_follows_course",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "taskId"))
    @Builder.Default private Set<User> studentsFollowingCourse = new HashSet<>();

    public void addUserToCourse(User user){
        this.studentsFollowingCourse.add(user);
    }



}
