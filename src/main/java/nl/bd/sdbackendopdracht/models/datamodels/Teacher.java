package nl.bd.sdbackendopdracht.models.datamodels;

import lombok.*;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Teacher extends User{
    @Column
    private int teacherNumber;
    @Column
    private Boolean isActiveTeacher;

    public Teacher(String firstName, String middleName, String lastName, RoleEnums roleEnums, String email, LocalDate dateOfCreation, LocalDate dateOfBirth, String password, int teacherNumber, Boolean isActiveTeacher) {
        super(firstName, middleName, lastName, roleEnums, email, dateOfCreation, dateOfBirth, password);
        this.teacherNumber = teacherNumber;
        this.isActiveTeacher = isActiveTeacher;
    }
}
