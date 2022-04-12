package nl.bd.sdbackendopdracht.models.requestmodels;

import jdk.jfr.Enabled;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TaskRegistrationRequest {
    private final String taskName;
    private final String taskDescription;
    private final LocalDateTime taskDeadline;
}
