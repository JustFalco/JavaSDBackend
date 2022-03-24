package nl.bd.sdbackendopdracht.controllers;

import nl.bd.sdbackendopdracht.enums.RoleEnums;
import nl.bd.sdbackendopdracht.models.Student;
import nl.bd.sdbackendopdracht.repos.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ApiControllers {

    @Autowired
    private StudentRepo studentRepo;



}
