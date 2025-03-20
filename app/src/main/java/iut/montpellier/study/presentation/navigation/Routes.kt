//package iut.montpellier.study.presentation.navigation
//import kotlinx.serialization.Serializable
//
//sealed class Routes {
//
//    @Serializable
//    object HomeScreens
//    @Serializable
//    object IncriptionScreens
//}

package iut.montpellier.study.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes{

    @Serializable
    object HomeScreens
    @Serializable
    object IncriptionScreens
    @Serializable
    object LoginScreens
    @Serializable
    object ConnexionScreens
    @Serializable
    object CourseListScreen
    @Serializable
    object CourseDetailScreen  { // Example with argument
        fun createRoute(courseId: Int): String {
            return "course_detail_screen/$courseId"
        }
    }

    @Serializable
    object ExerciseScreen {
        fun createRoute(chapterId: Int): String {
            return "exercise_screen/$chapterId"
        }
    }
    @Serializable
    object ProgressScreen
    @Serializable
    object ForgotPasswordScreen
    @Serializable
    object ProfileScreen
    @Serializable
    object SplashScreen
}