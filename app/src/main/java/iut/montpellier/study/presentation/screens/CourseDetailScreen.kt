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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import iut.montpellier.study.data.ChapterViewModel
import iut.montpellier.study.data.CourseViewModel
import iut.montpellier.study.data.entity.Chapter
import iut.montpellier.study.data.entity.Course
import iut.montpellier.study.presentation.navigation.Routes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import iut.montpellier.study.data.CourseEvent
import iut.montpellier.study.data.ChapterEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    navController: NavController,
    courseId: Int,
    courseViewModel: CourseViewModel = hiltViewModel(),
    chapterViewModel: ChapterViewModel = hiltViewModel()
) {
    val chapters = chapterViewModel.getChaptersByCourseId(courseId).collectAsState(initial = emptyList()).value
    var course by remember { mutableStateOf<Course?>(null) }
    val chapterState = chapterViewModel.state.collectAsState().value // Get the state of ChapterViewModel
    var newChapterTitle by remember { mutableStateOf("") }
    var newChapterDescription by remember { mutableStateOf("") }
    var newChapterContent by remember { mutableStateOf("") }

    LaunchedEffect(courseId) {
        course = courseViewModel.getCourseById(courseId)
    }
    LaunchedEffect(Unit) {
        course?.let {
            println("course title : ${it.title}")
            println("course description : ${it.description}")
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                chapterViewModel.onEvent(ChapterEvent.OnShowDialog(true))
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
            course?.let { course ->
                Text(text = course.title, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = course.description, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Chapters", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(chapters) { chapter ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Routes.ChapterDetail.createRoute(chapter.id))
                        }
                    ) {
                        Column {
                            Text(text = chapter.title, fontSize = 18.sp)
                            Text(text = chapter.description)
                            Text(text = chapter.content)
                        }
                    }
                }
            }
        }
        if (chapterState.isAddingChapter) { // Corrected line: Use chapterState
            AlertDialog(
                onDismissRequest = {
                    chapterViewModel.onEvent(ChapterEvent.OnShowDialog(false))
                },
                title = { Text("Add New Chapter") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newChapterTitle,
                            onValueChange = { newChapterTitle = it },
                            label = { Text("Title") }
                        )
                        OutlinedTextField(
                            value = newChapterDescription,
                            onValueChange = { newChapterDescription = it },
                            label = { Text("Description") }
                        )
                        OutlinedTextField(
                            value = newChapterContent,
                            onValueChange = { newChapterContent = it },
                            label = { Text("Content") }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        val newChapter = Chapter(
                            title = newChapterTitle,
                            description = newChapterDescription,
                            content = newChapterContent,
                            courseId = courseId
                        )
                        chapterViewModel.onEvent(ChapterEvent.SaveChapter(newChapter))
                        chapterViewModel.onEvent(ChapterEvent.OnShowDialog(false))
                        newChapterTitle = ""
                        newChapterDescription = ""
                        newChapterContent = ""
                    }) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        chapterViewModel.onEvent(ChapterEvent.OnShowDialog(false))
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}