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
public class Administrator extends User{
    @Column
    private int workerNumber;
    @Column
    private Boolean isActiveWorker;

    public Administrator(String firstName, String middleName, String lastName, RoleEnums roleEnums, String email, LocalDate dateOfCreation, LocalDate dateOfBirth, String password, int workerNumber, Boolean isActiveWorker) {
        super(firstName, middleName, lastName, roleEnums, email, dateOfCreation, dateOfBirth, password);
        this.workerNumber = workerNumber;
        this.isActiveWorker = isActiveWorker;
    }
}
