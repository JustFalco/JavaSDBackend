package nl.bd.sdbackendopdracht.services;

import lombok.AllArgsConstructor;
import nl.bd.sdbackendopdracht.models.datamodels.StudentGrades;
import nl.bd.sdbackendopdracht.repositories.GradeRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;


    //Get one grade
    public StudentGrades getStudentGrade(Long gradeId){
        //TODO validation
        return gradeRepository.getById(gradeId);
    }
    //get all grades from student

    //get all grades in course

    //change one grade

    //submit grade for all students in course

    //delete grade
    public void deleteGrade(Long gradeId){
        gradeRepository.deleteById(gradeId);
    }

}
