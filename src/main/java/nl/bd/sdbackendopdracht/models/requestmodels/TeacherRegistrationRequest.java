/*
 * Copyright (c) 2022. Falco Wolkorte
 */

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
public class TeacherRegistrationRequest {
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String email;
    private final LocalDate dateOfBirth;
    private final String password;
    private final int teacherNumber;
    private final boolean isActiveTeacher;

}
