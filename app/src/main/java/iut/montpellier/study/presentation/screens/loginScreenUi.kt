package iut.montpellier.study.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import iut.montpellier.study.R
import iut.montpellier.study.data.AppState
import iut.montpellier.study.data.LoginState
import iut.montpellier.study.data.UserViewModel
import iut.montpellier.study.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api:: class)
@Composable
fun LoginScreenUI(
    state: AppState = AppState(),
    viewModel: UserViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val loginState by viewModel.loginState.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo and Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_edulms),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "Study App",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Email Field
            OutlinedTextField(
                value = state.email.value, // Access values via state.value
                onValueChange = {
                    state.email.value=it
                },
                label = { Text("Email") },
                placeholder = { Text("Enter your email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password Field
            OutlinedTextField(
                value = state.password.value, // Access values via state.value
                onValueChange = {
                    state.password.value=it
                },
                label = { Text("Password") },
                placeholder = { Text("Enter your password") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            Button(
                onClick = {
                    viewModel.loginUser(state.email.value, state.password.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text(text = "Commencer", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Create an account Button
            Button(
                onClick = { navController.navigate(Routes.Inscription.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text(text = "je n'ai pas de compte", fontSize = 18.sp)
            }
            when (loginState) {
                is LoginState.Idle -> {}
                is LoginState.Loading -> {
                    CircularProgressIndicator()
                }
                is LoginState.Success -> {
                    navController.navigate(Routes.Home.route)
                }
                is LoginState.Error -> {
                    Text(text = (loginState as LoginState.Error).message)
                }
            }
        }
    }
}