package nl.bd.sdbackendopdracht.models.requestmodels;

import java.time.LocalDateTime;



public record TaskRegistrationRequest(String taskName, String taskDescription, LocalDateTime taskDeadline) {
}
