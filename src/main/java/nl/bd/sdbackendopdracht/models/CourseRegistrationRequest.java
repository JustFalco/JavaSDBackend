package nl.bd.sdbackendopdracht.models;

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
    private Long belongsToSchoolId;
    private Long teacherGivesCourseId;

}
