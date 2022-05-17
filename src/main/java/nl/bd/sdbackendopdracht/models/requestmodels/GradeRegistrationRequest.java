package nl.bd.sdbackendopdracht.models.requestmodels;


import java.time.LocalDate;


public record GradeRegistrationRequest(String description, float grade, int weight, LocalDate testDate,
                                       Long markBelongsToTaskId) {
}
