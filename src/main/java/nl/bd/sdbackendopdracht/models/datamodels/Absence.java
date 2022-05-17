package nl.bd.sdbackendopdracht.models.datamodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import nl.bd.sdbackendopdracht.security.enums.AbsenceTypes;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
@Table(name = "absence")
public class Absence {
    @Id
    @SequenceGenerator(
            name = "absence_sequencegenerator",
            sequenceName = "absence_sequencegenerator",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "absence_sequencegenerator",
            strategy = GenerationType.SEQUENCE
    )
    private Long absenceId;

    @JsonIgnore
    @ManyToOne
    private User absentStudent;
    @ManyToOne
    private User submittedByAdministrator;

    @Enumerated(value = EnumType.STRING)
    private AbsenceTypes absenceType;

    private String absenceDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Absence absence = (Absence) o;
        return absenceId != null && Objects.equals(absenceId, absence.absenceId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
