package nl.bd.sdbackendopdracht.controllers;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.Absence;
import nl.bd.sdbackendopdracht.models.requestmodels.AbsenceRegistrationRequest;
import nl.bd.sdbackendopdracht.services.AbsenceService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class AbsenceController {

    public final AbsenceService absenceService;

    //Submit absence (Administrator)
    @PostMapping("/administrator/absence/submit")
    public Absence submitAbsence(@RequestBody AbsenceRegistrationRequest absenceRegistrationRequest, Authentication authentication){
        return absenceService.submitAbsence(absenceRegistrationRequest, authentication);
    }

    //Get absence info(All)
    @GetMapping("/absence/absenceId={absenceId}")
    public Absence getAbsenceData(@PathVariable("absenceId") Long absenceId){
        return absenceService.getAbsenceDetails(absenceId);
    }

    //Change absence (Administrator)
    @PutMapping("/administrator/absence/change?absence={absenceId}")
    public Absence changeAbsence(@PathVariable("absenceId") Long absenceId,
                                 Authentication authentication,
                                 @RequestBody AbsenceRegistrationRequest absenceRegistrationRequest){
        return absenceService.changeAbsence(absenceId, absenceRegistrationRequest, authentication);
    }

    //Delete absence (Administrator)
    @DeleteMapping("/administrator/absence/delete?absenceId={absenceId}")
    public void deleteAbsence(@PathVariable("absenceId") Long absenceId){
        absenceService.removeAbsence(absenceId);
    }

    //Get all absence from student (all)
    @GetMapping("/absence/get_absence/student={userId}")
    public Set<Absence> getAbsenceFromStudent(@PathVariable("userId") Long userId){
        return absenceService.getAbsenceFromStudent(userId);
    }
}
