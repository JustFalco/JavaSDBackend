package nl.bd.sdbackendopdracht.models;

import nl.bd.sdbackendopdracht.enums.RoleEnums;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Teacher extends User{
    @Column
    private int teacherNumber;
    @Column
    private Boolean isActiveTeacher;

    public Teacher(Long id, String firstName, String middleName, String lastName, String email, LocalDate dateOfCreation, LocalDate dateOfBirth, String password, int teacherNumber, Boolean isActiveTeacher) {
        super(id, firstName, middleName, lastName, RoleEnums.ROLE_TEACHER, email, dateOfCreation, dateOfBirth, password);
        this.teacherNumber = teacherNumber;
        this.isActiveTeacher = isActiveTeacher;
        this.setRoleEnums(RoleEnums.ROLE_TEACHER);
    }

    public Teacher(String firstName, String middleName, String lastName, String email, LocalDate dateOfCreation, LocalDate dateOfBirth, String password, int teacherNumber, Boolean isActiveTeacher) {
        super(firstName, middleName, lastName, RoleEnums.ROLE_TEACHER, email, dateOfCreation, dateOfBirth, password);
        this.teacherNumber = teacherNumber;
        this.isActiveTeacher = isActiveTeacher;
        this.setRoleEnums(RoleEnums.ROLE_TEACHER);
    }

    public Teacher() {
    }

    public int getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(int teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public Boolean getActiveTeacher() {
        return isActiveTeacher;
    }

    public void setActiveTeacher(Boolean activeTeacher) {
        isActiveTeacher = activeTeacher;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherNumber=" + teacherNumber +
                ", isActiveTeacher=" + isActiveTeacher +
                '}';
    }
}
