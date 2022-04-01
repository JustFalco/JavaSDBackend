package nl.bd.sdbackendopdracht.models.datamodels;

import lombok.*;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "user_sequencegenerator",
            sequenceName = "user_sequencegenerator",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "user_sequencegenerator",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @Column
    private String firstName;
    @Column
    private String middleName;
    @Column
    private String lastName;
    @Column
    @Enumerated(EnumType.STRING)
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

    private Boolean locked = false;
    private Boolean enabled = true;

    @ManyToOne
    private School school;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.roleEnums.name());
        return Collections.singletonList(authority);
    }



    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
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
                ", age=" + age +
                ", locked=" + locked +
                ", enabled=" + enabled +
                '}';
    }
}
