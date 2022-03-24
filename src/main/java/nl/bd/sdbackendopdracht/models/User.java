package nl.bd.sdbackendopdracht.models;

import nl.bd.sdbackendopdracht.enums.RoleEnums;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;
    @Column
    private String middleName;
    @Column
    private String lastName;
    @Column
    private RoleEnums roleEnums;
    @Column
    private String email;
    @Column
    private LocalDate dateOfCreation;
    @Column
    private LocalDate dateOfBirth;
    @Column
    private String password;

    @Transient
    private Integer age;

    public User() {
    }

    public User(Long id, String firstName, String middleName, String lastName, RoleEnums roleEnums, String email, LocalDate dateOfCreation, LocalDate dateOfBirth, String password) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.roleEnums = roleEnums;
        this.email = email;
        this.dateOfCreation = dateOfCreation;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
    }

    public User(String firstName, String middleName, String lastName, RoleEnums roleEnums, String email, LocalDate dateOfCreation, LocalDate dateOfBirth, String password) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.roleEnums = roleEnums;
        this.email = email;
        this.dateOfCreation = dateOfCreation;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
    }

    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RoleEnums getRoleEnums() {
        return roleEnums;
    }

    public void setRoleEnums(RoleEnums roleEnums) {
        this.roleEnums = roleEnums;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roleEnums=" + roleEnums +
                ", email='" + email + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfBirth=" + dateOfBirth +
                ", password='" + password + '\'' +
                '}';
    }
}
