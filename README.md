# Project Progen API documentation
Voor de eindopdracht van software development heb ik een web API gemaakt genaamd project Progen. Deze API is qua functionaliteit geïnspireerd van mijn vorige java project: Magister2.0.  
Project Progen is een API voor school beheer software. Zo geeft deze API de mogelijkheid voor docenten, studenten en administratief medewerkers om:
- In te loggen
- Opdrachten te geven en te maken
- Cijfers te bekijken en in te voeren
- Cursussen aan te bieden
- Bestanden voor taken te up- en downloaden
- En nog veel meer

## Documentatie
Voor gedetailleerde documentatie, zie de folder /documentatie

## Github
Voor versiebeheer van de applicatie is er gebruik gemaakt van Github. De repository van de applicatie is terug te vinden op: https://github.com/JustFalco/JavaSDBackend

## Postman
Aangezien deze API nog niet over een grafiche web interfase beschikt, zijn er in de folder /postman twee collectie bestanden toegevoegd. Hiermee wordt het makkelijker om de appliatie te testen zonder de browser te gebruiken. Deze bestanden kunnen in postman geïmporteerd worden en bevatten alle endpoints en vooringevulde data. Om de docker applicatie te testen in postman, gebruik dan het bestand All endpoints Docker.postman_collection.json. Anders gebruik All endpoints.postman_collection.json.

## Installatie
De installatie kan op twee verschillende manieren worden uitgevoerd: handmatig of via docker.
Voor de handmatige installatie, zie het bestand Installatiehandleiding.md in de folder /documentatie. Voor de snelle installatie via docker, volg 'Applicatie installeren via docker'.

### Applicatie installeren via docker
(Deze optie werkt **waarschijnlijk** alleen op de x86-64 architectuur, maar probeer het gerust uit op andere platformen)

Om de applicatie te draaien in een container, is het belangrijk dat docker is geinstalleerd. Volg hiervoor één van de volgende handleidingen:
- [Windows](https://docs.docker.com/desktop/windows/install/)
- [Linux](https://docs.docker.com/engine/install/ubuntu/)
- [MacOS](https://docs.docker.com/desktop/mac/install/)

Zorg er voor dat tijdens het installeren van docker ook WSL geinstalleerd word, anders kunnen er rare bugs ontstaan, zie (stap 5: https://docs.microsoft.com/nl-nl/windows/wsl/install-manual#step-4---download-the-linux-kernel-update-package)

Na het installeren van docker:
1. Pak het zip bestand van de applicatie uit
2. Open de applicatie in een tekst bewerker naar keuze (Notepad++, VSCode, IntelliJ IDEA, Atom)
3. Open de terminal van de tekst bewerker en voer het volgende commando uit
> docker-compose up --build
4. Na een tijdje zou de applicatie op localhost:8081 moeten draaien en is de OpenAPI documentatie te vinden op http://localhost:8081/swagger-ui/index.html en is de SMPT mail server te vinden http://localhost:1080/


## Endpoints
De applicatie kan op de volgende endpoints benaderd worden. Deze endpoints zijn ook terug te vinden in de OpenAPI documentatie op http://localhost:8080/swagger-ui.html of http://localhost:8081/swagger-ui.html (voor docker gebruikers). Deze documentatie werkt uiteraard alleen als de applicatie actief is.

## user-controller
- [GET] /api/v1/user/get_details
- [GET] /api/v1/user/get_details/user={userId}
- [GET] /api/v1/user/get_details/email={email}
- [PUT] /api/v1/user/change/email={email} \
  Request body:
```
{
  "firstName": "string",
  "middleName": "string",
  "lastName": "string",
  "email": "string@string.nl",
  "dateOfBirth": "2022-05-24",
  "password": "Secur3P@ss",
  "studentYear": 0,
  "isActive": true
}
```
- [DELETE] /api/v1/admin/delete/user={userId}

## Task-controller
- [GET] /api/v1/task/get_tasks_from_student/student={userId}
- [GET] /api/v1/task/get_taskfiles/task={taskId}
- [GET] /api/v1/task/get_taskfiles/file={fileId}
- [GET] /api/v1/task/get_taskdetails/task={taskId}
- [GET] /api/v1/admin/coffee
- [POST] /api/v1/teacher/task/create_task \
  Request body:
```
{
  "taskName": "string",
  "taskDescription": "string",
  "taskDeadline": "2022-05-24T17:28:20.640Z"
}
```
- [POST] /api/v1/teacher/task/create_task/course={courseId} \
  Request body:
```
{
  "taskName": "string",
  "taskDescription": "string",
  "taskDeadline": "2022-05-24T17:28:20.640Z"
}
```
- [POST] /api/v1/teacher/task/add_file/task={taskId} \
  Request body:
```
[] array of files
```
- [PUT] /api/v1/teacher/task/change/task={taskId} \
  Request body:
```
{
  "taskName": "string",
  "taskDescription": "string",
  "taskDeadline": "2022-05-24T17:29:59.359Z"
}
```
- [PUT] /api/v1/teacher/task/add_student/student={userId}&task={taskId}
- [DELETE] /api/v1/teacher/task/delete/task={taskId}
- [DELETE] /api/v1/teacher/task/delete/student={userId}&task={taskId}

## Grade-controller
- [GET] /api/v1/student/grades/get_grade/last/student={userId}
- [GET] /api/v1/grades/get_grade/overview/student={userId}
- [GET] /api/v1/grades/get_grade/grade={gradeId}
- [POST] /api/v1/teacher/grades/submit_grade/student={studentId} \
  Request body:
```
{
  "description": "string",
  "grade": 1,
  "weight": 1,
  "testDate": "2022-05-24",
  "markBelongsToTaskId": 0
}
```
- [PUT] /api/v1/teacher/grade/change_grade/student={studentId}&grade={gradeId} \
  Request body:
```
{
  "description": "string",
  "grade": 1,
  "weight": 1,
  "testDate": "2022-05-24",
  "markBelongsToTaskId": 0
}
```
- [DELETE] /api/v1/teacher/grades/delete/grade={gradeId}

## Course-controller
- [GET] /api/v1/user/course/get_details/course={courseId}
- [POST] /api/v1/administrator/course/create \
  Request body:
```
{
  "courseName": "string",
  "courseDescription": "string",
  "teacherGivesCourseId": 1
}
```
- [POST] /api/v1/administrator/course/add_students/course={courseId} \
  Request body:
```
[
   2
]
```
- [POST] /api/v1/administrator/course/add_student/student={studentId}&course={courseId}
- [PUT] /api/v1/administrator/course/change/course={courseId} \
  Request body:
```
{
  "courseName": "string",
  "courseDescription": "string",
  "teacherGivesCourseId": 1
}
```
- [DELETE] /api/v1/administrator/course/remove/course={courseId}
- [DELETE] /api/v1/administrator/course/delete_student/student={studentId}&course={courseId}

## Absence-controller
- [GET] /api/v1/absence/get_absence/student={userId}
- [GET] /api/v1/absence/absenceId={absenceId}
- [POST] /api/v1/administrator/absence/submit \
  Request body:
```
{
  "absentStudent": 2,
  "absenceType": "ABSENT",
  "absenceDescription": "string"
}
```
- [PUT] /api/v1/administrator/absence/change/absence={absenceId} \
  Request body:
```
{
  "absentStudent": 2,
  "absenceType": "ABSENT",
  "absenceDescription": "string"
}
```
- [DELETE] /api/v1/administrator/absence/delete/absenceId={absenceId}

## Registration-controller
- [POST] /api/v1/registration/register_school \
  Request body:
```
{
  "schoolName": "string",
  "schoolMail": "string@string.nl"
}
```
- [POST] /api/v1/administrator/registration/register_teacher \
  Request body:
```
{
  "firstName": "string",
  "middleName": "string",
  "lastName": "string",
  "email": "string@string.nl",
  "dateOfBirth": "2022-05-24",
  "password": "Secur3P@ss",
  "studentYear": 0,
  "isActive": true
}
```
- [POST] /api/v1/administrator/registration/register_student \
  Request body:
```
{
  "firstName": "string",
  "middleName": "string",
  "lastName": "string",
  "email": "string@string.nl",
  "dateOfBirth": "2022-05-24",
  "password": "Secur3P@ss",
  "studentYear": 0,
  "isActive": true
}
```
- [POST] /api/v1/administrator/registration/register_administrator \
  Request body:
```
{
  "firstName": "string",
  "middleName": "string",
  "lastName": "string",
  "email": "string@string.nl",
  "dateOfBirth": "2022-05-24",
  "password": "Secur3P@ss",
  "studentYear": 0,
  "isActive": true
}
```

:)
