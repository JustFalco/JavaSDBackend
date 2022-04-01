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
public class Student extends User{
    @Column
    private int StudentNumber;
    @Column
    private int year;
    @Column
    private Boolean isActiveStudent;


    public Student(String firstName, String middleName, String lastName, RoleEnums roleEnums, String email, LocalDate dateOfCreation, LocalDate dateOfBirth, String password, int studentNumber, int year, Boolean isActiveStudent) {
        super(firstName, middleName, lastName, roleEnums, email, dateOfCreation, dateOfBirth, password);
        StudentNumber = studentNumber;
        this.year = year;
        this.isActiveStudent = isActiveStudent;
    }

}
