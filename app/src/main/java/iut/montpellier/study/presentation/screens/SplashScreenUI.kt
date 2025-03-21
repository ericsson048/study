package iut.montpellier.study.presentation.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import iut.montpellier.study.R
import iut.montpellier.study.presentation.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreenUI(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(3000) // Delay for 3 seconds
        navController.navigate(Routes.Welcome.route) {
            popUpTo(Routes.Splash.route) { inclusive = true } // Remove splash from backstack
        }
    }
    Splash()
}

@Composable
fun Splash(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ){
        Image(painter = painterResource(id = R.drawable.logo_edulms), contentDescription = "logo", modifier = Modifier.size(90.dp)
            .clip(CircleShape))
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    val navController = rememberNavController()
    SplashScreenUI(navController)
}