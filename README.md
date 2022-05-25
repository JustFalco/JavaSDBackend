# Project Progen API documentation
Project progen is an API that allows schools to manage their students, teachers, courses and much more.

## Documentatie

## Installatie

## Docker

## Postman

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