package nl.bd.sdbackendopdracht.models.datamodels;

import lombok.*;
import nl.bd.sdbackendopdracht.security.enums.AbsenceTypes;

import javax.persistence.*;

@Entity
@Table(name = "absence")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
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

    @ManyToOne
    private User absentStudent;
    @ManyToOne
    private User submittedByAdministrator;

    @Enumerated(value = EnumType.STRING)
    private AbsenceTypes absenceType;

    private String absenceDescription;
}
