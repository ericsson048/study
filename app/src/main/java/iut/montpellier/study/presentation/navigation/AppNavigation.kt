package iut.montpellier.study.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import iut.montpellier.study.data.UserViewModel
import iut.montpellier.study.presentation.screens.InscriptionScreenUI
import iut.montpellier.study.presentation.screens.LoginScreenUI
import iut.montpellier.study.presentation.screens.SplashScreenUI
import iut.montpellier.study.presentation.screens.WelcomeScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import iut.montpellier.study.presentation.screens.CourseDetailScreen
import iut.montpellier.study.presentation.screens.CourseScreen
import iut.montpellier.study.presentation.screens.HomeScreen
import iut.montpellier.study.presentation.screens.ChapterDetailScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val state = viewModel.state.collectAsState()
    NavHost(navController = navController, startDestination = Routes.Splash.route) {
        composable(Routes.Splash.route) {
            SplashScreenUI(navController)
        }
        composable(Routes.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(Routes.Home.route) {
            HomeScreen(navController)
        }
        composable(Routes.Inscription.route) {
            InscriptionScreenUI(
                navController = navController,
                state = state.value,
                onEvent = { viewModel.insertUser() }
            )
        }
        composable(Routes.Login.route) {
            LoginScreenUI(navController = navController, viewModel = viewModel, state = state.value)
        }
        composable(Routes.CourseList.route) {
            CourseScreen(navController = navController)
        }
        composable(
            route = "${Routes.ROUTE_COURSE_DETAIL}/{${Routes.COURSE_ID_ARG}}",
            arguments = listOf(navArgument(Routes.COURSE_ID_ARG) { type = NavType.IntType })
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getInt(Routes.COURSE_ID_ARG)
            if (courseId != null) {
                CourseDetailScreen(navController = navController, courseId = courseId)
            }
        }
        composable(
//            route = Routes.ChapterDetail(0).route, // Use the property directly
            route = "${Routes.ROUTE_CHAPTER_DETAIL}/{${Routes.CHAPTER_ID_ARG}}",
            arguments = listOf(navArgument(Routes.CHAPTER_ID_ARG) { type = NavType.IntType })
        ) { backStackEntry ->
            val chapterId = backStackEntry.arguments?.getInt(Routes.CHAPTER_ID_ARG)
            if (chapterId != null) {
                ChapterDetailScreen(navController = navController, chapterId = chapterId)
            }
        }
    }
}