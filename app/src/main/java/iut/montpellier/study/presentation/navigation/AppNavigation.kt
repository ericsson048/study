package iut.montpellier.study.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import iut.montpellier.study.data.UserViewModel
import iut.montpellier.study.presentation.screens.LoginScreenUI
import iut.montpellier.study.presentation.screens.inscriptionScreensUI


@Composable
fun AppNavigation(modifier: Modifier =Modifier,viewModel: UserViewModel= hiltViewModel()){

    val navController = rememberNavController()
    val state = viewModel.state.collectAsState()
    NavHost(navController = navController, startDestination = Routes.HomeScreens){
        composable<Routes.HomeScreens> {
            Box(modifier = Modifier.fillMaxSize(),){
                Button(onClick = {
                    navController.navigate(Routes.IncriptionScreens)
                }) {
                    Text("Inscrivez-vous")
                }
            }
        }
        composable<Routes.IncriptionScreens> { inscriptionScreensUI(state = state.value,onEvent={viewModel.insertUser()},navController = navController) }
        composable<Routes.LoginScreens> { LoginScreenUI(state = state.value, onEvent ={viewModel}, navController = navController)  }
    }
}