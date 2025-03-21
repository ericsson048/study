package iut.montpellier.study.data


import iut.montpellier.study.data.entity.Course

sealed class CourseEvent {
    data class AddCourse(val course: Course) : CourseEvent()
    data class OnTitleChange(val title: String) : CourseEvent()
    data class OnDescriptionChange(val description: String) : CourseEvent()
    data class OnShowDialog(val value: Boolean) : CourseEvent()
    object GetAllCourses : CourseEvent()
}