package iut.montpellier.study.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.navigation.NavController
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import iut.montpellier.study.R
import iut.montpellier.study.presentation.navigation.Routes
import iut.montpellier.study.ui.theme.CustomShapes
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavController) {



    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        MyImage()
        Spacer(modifier = Modifier.height(10.dp))
        TopBar(navController = navController)
    }



}

@Composable
fun MyImage(){
    Image(
        painter = painterResource(id = R.drawable.java),
        contentDescription = "",
        //modifier = Modifier.size(200.dp)
    )
}

@Composable
fun TopBar(navController: NavController){

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        //.background(colorResource(id = R.color.purple_500))
    ){
        Text(
            text = "Vous etez sur un meilleur  \n endroit pour apprendre",
            color = colorResource(id = R.color.black),
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            // modifier = Modifier.padding(15.dp)
        )

        Spacer(modifier = Modifier.width(40.dp))

        Text(
            text="Connectez-vouz a notre application avant d'apprender",
            style = TextStyle(textAlign = TextAlign.Center),
            color = colorResource(id = R.color.gray),
            modifier = Modifier.padding(20.dp)
        )

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {


            // Spacer(modifier = Modifier.width(30.dp))

            Button(
                onClick = { navController.navigate(Routes.Login.route) },
                shape = CustomShapes.medium,
                modifier = Modifier
                    .size(width = 320.dp, height = 40.dp)
            ){
                Text( text = "Authentification")
            }

        }

    }
}