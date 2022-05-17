package nl.bd.sdbackendopdracht.models.requestmodels;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserRegistrationRequest {
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String email;
    private final LocalDate dateOfBirth;
    private final String password;
    private final int teacherNumber;
    private final boolean isActiveTeacher;
    private final int studentNumber;
    private final int studentYear;
    private final int workerNumber;
    private final boolean isActiveWorker;
    private final boolean isActiveStudent;
}
