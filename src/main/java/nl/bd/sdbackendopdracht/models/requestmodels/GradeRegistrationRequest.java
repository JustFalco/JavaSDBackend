package nl.bd.sdbackendopdracht.models.requestmodels;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class  GradeRegistrationRequest {
    private String description;
    private float grade;
    private int weight;
    private LocalDate testDate;
    private Long markBelongsToTaskId;
}
