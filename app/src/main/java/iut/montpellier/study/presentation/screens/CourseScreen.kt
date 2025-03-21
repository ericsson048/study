package iut.montpellier.study.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import iut.montpellier.study.data.CourseEvent
import iut.montpellier.study.data.CourseViewModel
import iut.montpellier.study.data.entity.Course
import iut.montpellier.study.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseScreen(navController: NavController, viewModel: CourseViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(CourseEvent.OnShowDialog(true))
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            if (state.isShowDialog) {
                AlertDialog(
                    onDismissRequest = { viewModel.onEvent(CourseEvent.OnShowDialog(false)) },
                    title = { Text(text = "Create a course") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = state.title.value,
                                onValueChange = { viewModel.onEvent(CourseEvent.OnTitleChange(it)) },
                                label = { Text("Title") }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = state.description.value,
                                onValueChange = { viewModel.onEvent(CourseEvent.OnDescriptionChange(it)) },
                                label = { Text("Description") }
                            )
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.onEvent(CourseEvent.AddCourse(Course(title = state.title.value, description = state.description.value)))
                        }) {
                            Text(text = "Create")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { viewModel.onEvent(CourseEvent.OnShowDialog(false)) }) {
                            Text(text = "Cancel")
                        }
                    }
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(state.allCourses) { course ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Routes.CourseDetail.createRoute(course.id))
                        }
                    ) {
                        Column {
                            Text(text = course.title, fontSize = 18.sp)
                            Text(text = course.description)
                        }
                    }
                }
            }
        }
    }
}