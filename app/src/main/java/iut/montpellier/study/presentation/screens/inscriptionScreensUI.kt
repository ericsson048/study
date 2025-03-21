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
import androidx.navigation.compose.rememberNavController
import iut.montpellier.study.R
import iut.montpellier.study.data.AppState
import iut.montpellier.study.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InscriptionScreenUI(
    modifier: Modifier = Modifier,
    onEvent: () -> Unit = {},
    state: AppState = AppState(),
    navController: NavHostController = rememberNavController()
) {

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo and Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_edulms),
                    contentDescription = null,
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

            // Input Fields
            OutlinedTextField(
                value = state.first_name.value,
                onValueChange = { state.first_name.value = it },
                placeholder = { Text("Entrez votre Nom") },
                label = { Text("Nom") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = state.last_name.value,
                onValueChange = { state.last_name.value=it },
                placeholder = { Text("Entrez votre Prénom") },
                label = { Text("Prénom") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = state.email.value,
                onValueChange = { state.email.value=it },
                placeholder = { Text("Entrez votre Email") },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = state.password.value,
                onValueChange = { state.password.value=it },
                placeholder = { Text("Entrez votre Mot de Passe") },
                label = { Text("Mot de Passe") },
                modifier = Modifier.fillMaxWidth(),

                )

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            Button(
                onClick = {
                    onEvent()
                    navController.navigate(Routes.Home.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Soumettre", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Already have an account Button
            Button(
                onClick = { navController.navigate(Routes.Login.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text(text = "j'ai un compte", fontSize = 18.sp)
            }
        }
    }
}