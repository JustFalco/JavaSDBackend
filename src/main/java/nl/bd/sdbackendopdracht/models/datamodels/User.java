package nl.bd.sdbackendopdracht.models.datamodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import nl.bd.sdbackendopdracht.security.enums.RoleEnums;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
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
    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private RoleEnums roleEnums;
    @Column(
            nullable = false
    )
    private String email;
    private LocalDate dateOfCreation;
    private LocalDate dateOfBirth;
    private String password;
    private int workerNumber;
    private Boolean isActiveWorker;
    private int studentNumber;
    private int year;
    private Boolean isActiveStudent;
    private int teacherNumber;
    private Boolean isActiveTeacher;
    @Transient
    private Integer age;
    private Boolean locked = false;
    private Boolean enabled = true;
    @ManyToOne
    private School school;

    @OneToMany(mappedBy = "markBelongsToStudent")
    private List<StudentGrades> grades;

    @JsonIgnore
    @ManyToMany(mappedBy = "taskHasUsers")
    @Builder.Default
    @ToString.Exclude
    private Set<Task> userHasTasks = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "studentsFollowingCourse")
    @Builder.Default
    @ToString.Exclude
    private Set<Course> coursesBelongingToStudent = new HashSet<>();

    public Integer getAge() {
        if (this.dateOfBirth == null) {
            return 0;
        }
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

    public Long getUserId() {
        return userId;
    }
}
