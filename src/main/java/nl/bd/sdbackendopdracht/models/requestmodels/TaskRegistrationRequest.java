package nl.bd.sdbackendopdracht.models.requestmodels;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
