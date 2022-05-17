package nl.bd.sdbackendopdracht.models.requestmodels;

import java.time.LocalDate;

public record UserRegistrationRequest(String firstName, String middleName, String lastName, String email,
                                      LocalDate dateOfBirth, String password, int studentYear,
                                      Boolean isActive) {
}
