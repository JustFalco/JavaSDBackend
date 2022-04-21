package nl.bd.sdbackendopdracht.models.requestmodels;

import lombok.*;
import nl.bd.sdbackendopdracht.models.datamodels.User;
import nl.bd.sdbackendopdracht.security.enums.AbsenceTypes;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AbsenceRegistrationRequest {
    private Long absentStudent;
    private AbsenceTypes absenceType;
    private String absenceDescription;
}
