package nl.bd.sdbackendopdracht.models;

import jdk.jfr.Enabled;
import lombok.*;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TaskRegistrationRequest {
    private final String taskName;
    private final String taskDescription;
    private final LocalDate taskDeadline;
}
