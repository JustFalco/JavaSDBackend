package nl.bd.sdbackendopdracht.models.requestmodels;

import nl.bd.sdbackendopdracht.security.enums.AbsenceTypes;


public record AbsenceRegistrationRequest(Long absentStudent, AbsenceTypes absenceType, String absenceDescription) {
}
