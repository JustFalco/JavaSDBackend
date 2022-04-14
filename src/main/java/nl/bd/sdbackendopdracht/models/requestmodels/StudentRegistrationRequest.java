package nl.bd.sdbackendopdracht.models.requestmodels;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StudentRegistrationRequest {
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String email;
    private final LocalDate dateOfBirth;
    private final String password;
    private final int studentNumber;
    private final int studentYear;
    private final RoleEnums roleEnums;
}
