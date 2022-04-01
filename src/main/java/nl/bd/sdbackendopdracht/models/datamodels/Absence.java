package nl.bd.sdbackendopdracht.models.datamodels;

import nl.bd.sdbackendopdracht.security.enums.AbsenceTypes;

import javax.persistence.*;

@Entity
public class Absence {
    @Id
    private Long absenceId;

    @ManyToOne
    private Student absentStudent;
    @ManyToOne
    private Administrator submittedByAdministrator;

    @Enumerated(value = EnumType.STRING)
    private AbsenceTypes absenceType;

    private String absenceDescription;
}
