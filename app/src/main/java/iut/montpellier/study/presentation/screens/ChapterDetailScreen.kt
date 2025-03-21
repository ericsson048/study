package iut.montpellier.study.presentation.screens

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import iut.montpellier.study.data.ChapterViewModel
import iut.montpellier.study.data.QuestionViewModel
import iut.montpellier.study.data.entity.Chapter
import iut.montpellier.study.data.entity.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterDetailScreen(
    navController: NavController,
    chapterId: Int,
    chapterViewModel: ChapterViewModel = hiltViewModel(),
    questionViewModel: QuestionViewModel = hiltViewModel()
) {
    var chapter by remember { mutableStateOf<Chapter?>(null) }
    val questions = questionViewModel.getQuestionsByChapterId(chapterId).collectAsState(initial = emptyList()).value
    LaunchedEffect(chapterId) {
        chapter = chapterViewModel.getChapterById(chapterId)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            chapter?.let { chapter ->
                Text(text = chapter.title, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = chapter.description, fontSize = 16.sp)
                Text(text = chapter.content, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Questions", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(questions) { question ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column {
                            Text(text = question.questionText, fontSize = 18.sp)
                            // Display other question details (options, correct answer) if needed
                        }
                    }
                }
            }
        }
    }
}