package nl.bd.sdbackendopdracht.models.requestmodels;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CourseRegistrationRequest {
    private String courseName;
    private String courseDescription;
    private Long teacherGivesCourseId;

}
