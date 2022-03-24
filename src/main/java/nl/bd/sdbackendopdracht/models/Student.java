package nl.bd.sdbackendopdracht.models;

import nl.bd.sdbackendopdracht.enums.RoleEnums;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Student extends User{
    @Column
    private int StudentNumber;
    @Column
    private int year;
    @Column
    private Boolean isActiveStudent;

    public Student() {
        this.setRoleEnums(RoleEnums.ROLE_STUDENT);
    }

    public Student(Long id, String firstName, String middleName, String lastName, String email, LocalDate dateOfCreation, LocalDate dateOfBirth, String password, int studentNumber, int year, Boolean isActiveStudent) {
        super(id, firstName, middleName, lastName, RoleEnums.ROLE_STUDENT, email, dateOfCreation, dateOfBirth, password);
        StudentNumber = studentNumber;
        this.year = year;
        this.isActiveStudent = isActiveStudent;
        this.setRoleEnums(RoleEnums.ROLE_STUDENT);
    }

    public Student(String firstName, String middleName, String lastName, String email, LocalDate dateOfCreation, LocalDate dateOfBirth, String password, int studentNumber, int year, Boolean isActiveStudent) {
        super(firstName, middleName, lastName, RoleEnums.ROLE_STUDENT, email, dateOfCreation, dateOfBirth, password);
        StudentNumber = studentNumber;
        this.year = year;
        this.isActiveStudent = isActiveStudent;
        this.setRoleEnums(RoleEnums.ROLE_STUDENT);
    }

    public int getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        StudentNumber = studentNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Boolean getActiveStudent() {
        return isActiveStudent;
    }

    public void setActiveStudent(Boolean activeStudent) {
        isActiveStudent = activeStudent;
    }

    @Override
    public String toString() {
        return "Student{" +
                "StudentNumber=" + StudentNumber +
                ", year=" + year +
                ", isActiveStudent=" + isActiveStudent +
                '}';
    }
}
