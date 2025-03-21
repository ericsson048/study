package iut.montpellier.study.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes(open val route: String) {  // open val route pour que les enfants l'héritent proprement
    object Splash : Routes(ROUTE_SPLASH)
    object Welcome : Routes(ROUTE_WELCOME)
    object Home : Routes(ROUTE_HOME)
    object Inscription : Routes(ROUTE_INSCRIPTION)
    object Login : Routes(ROUTE_LOGIN)
    object Connexion : Routes(ROUTE_CONNEXION)
    object Progress : Routes(ROUTE_PROGRESS)
    object ForgotPassword : Routes(ROUTE_FORGOT_PASSWORD)
    object Profile : Routes(ROUTE_PROFILE)
    object CourseList : Routes(ROUTE_COURSE_LIST)

    @Serializable
    data class CourseDetail(val courseId: Int) : Routes("$ROUTE_COURSE_DETAIL/$courseId") {
        companion object {
            fun createRoute(courseId: Int): String = "$ROUTE_COURSE_DETAIL/$courseId"
        }
    }

    @Serializable
    data class Exercise(val chapterId: Int) : Routes("$ROUTE_EXERCISE/$chapterId") {
        companion object {
            fun createRoute(chapterId: Int): String = "$ROUTE_EXERCISE/$chapterId"
        }
    }
    @Serializable
    data class ChapterDetail(val chapterId: Int) : Routes("$ROUTE_CHAPTER_DETAIL/$chapterId") {
        companion object {
            fun createRoute(chapterId: Int): String = "$ROUTE_CHAPTER_DETAIL/$chapterId"
        }
    }

    companion object {
        const val ROUTE_SPLASH = "splash_screen"
        const val ROUTE_WELCOME = "welcome_screen"
        const val ROUTE_HOME = "home_screen"
        const val ROUTE_INSCRIPTION = "inscription_screen"
        const val ROUTE_LOGIN = "login_screen"
        const val ROUTE_CONNEXION = "connexion_screen"
        const val ROUTE_COURSE_LIST = "course_list_screen"
        const val ROUTE_COURSE_DETAIL = "course_detail_screen"
        const val ROUTE_EXERCISE = "exercise_screen"
        const val ROUTE_PROGRESS = "progress_screen"
        const val ROUTE_FORGOT_PASSWORD = "forgot_password_screen"
        const val ROUTE_PROFILE = "profile_screen"
        const val ROUTE_CHAPTER_DETAIL = "chapter_detail_screen"
        // Argument keys
        const val COURSE_ID_ARG = "courseId"
        const val CHAPTER_ID_ARG = "chapterId"
    }
}