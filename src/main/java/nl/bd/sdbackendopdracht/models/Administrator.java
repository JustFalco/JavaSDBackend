package nl.bd.sdbackendopdracht.models;

import nl.bd.sdbackendopdracht.enums.RoleEnums;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Administrator extends User{
    @Column
    private int workerNumber;
    @Column
    private Boolean isActiveWorker;

    public Administrator() {
    }

    public Administrator(Long id, String firstName, String middleName, String lastName, String email, LocalDate dateOfCreation, LocalDate dateOfBirth, String password, int workerNumber, Boolean isActiveWorker) {
        super(id, firstName, middleName, lastName, RoleEnums.ROLE_ADMINISTRATOR, email, dateOfCreation, dateOfBirth, password);
        this.workerNumber = workerNumber;
        this.isActiveWorker = isActiveWorker;
        this.setRoleEnums(RoleEnums.ROLE_ADMINISTRATOR);
    }

    public Administrator(String firstName, String middleName, String lastName, String email, LocalDate dateOfCreation, LocalDate dateOfBirth, String password, int workerNumber, Boolean isActiveWorker) {
        super(firstName, middleName, lastName, RoleEnums.ROLE_ADMINISTRATOR, email, dateOfCreation, dateOfBirth, password);
        this.workerNumber = workerNumber;
        this.isActiveWorker = isActiveWorker;
        this.setRoleEnums(RoleEnums.ROLE_ADMINISTRATOR);
    }

    public int getWorkerNumber() {
        return workerNumber;
    }

    public void setWorkerNumber(int workerNumber) {
        this.workerNumber = workerNumber;
    }

    public Boolean getActiveWorker() {
        return isActiveWorker;
    }

    public void setActiveWorker(Boolean activeWorker) {
        isActiveWorker = activeWorker;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "workerNumber=" + workerNumber +
                ", isActiveWorker=" + isActiveWorker +
                '}';
    }
}
