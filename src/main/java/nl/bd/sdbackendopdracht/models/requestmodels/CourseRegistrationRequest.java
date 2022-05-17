package nl.bd.sdbackendopdracht.models.requestmodels;

public record CourseRegistrationRequest(String courseName, String courseDescription, Long teacherGivesCourseId) {
}
