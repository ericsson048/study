package iut.montpellier.study.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iut.montpellier.study.data.entity.Question
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuestionState(
    val questions: List<Question> = emptyList(),
    val isLoading: Boolean = false,
    val isAddingQuestion: Boolean = false,
    val isUpdatingQuestion: Boolean = false, // Add state for updating
    val questionSelected: Question? = null, // Add state for the question to update
    val error: String? = null
)

sealed interface QuestionEvent {
    data class SaveQuestion(val question: Question) : QuestionEvent
    data class DeleteQuestion(val question: Question) : QuestionEvent
    data class UpdateQuestion(val question: Question) : QuestionEvent
    data class OnShowDialog(val show: Boolean) : QuestionEvent
    data class OnLoading(val load: Boolean): QuestionEvent
    data class OnSelectQuestion(val question: Question): QuestionEvent // Add event to select question to update
    object OnClearError: QuestionEvent
}

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(QuestionState())
    val state = _state.asStateFlow()

    fun onEvent(event: QuestionEvent) {
        when (event) {
            is QuestionEvent.SaveQuestion -> {
                insertQuestion(event.question)
            }

            is QuestionEvent.DeleteQuestion -> {
                deleteQuestion(event.question)
            }

            is QuestionEvent.UpdateQuestion -> {
                updateQuestion(event.question)
            }
            is QuestionEvent.OnShowDialog -> {
                _state.update { it.copy(isAddingQuestion = event.show) }
            }
            is QuestionEvent.OnLoading -> {
                _state.update { it.copy(isLoading = event.load) }
            }
            is QuestionEvent.OnClearError -> {
                _state.update { it.copy(error = null) }
            }
            is QuestionEvent.OnSelectQuestion ->{
                _state.update { it.copy(questionSelected = event.question, isUpdatingQuestion = true, isAddingQuestion = true) } // set the state to update
            }
        }
    }

    private fun insertQuestion(question: Question) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.insertQuestion(question)
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun deleteQuestion(question: Question) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.deleteQuestion(question)
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun updateQuestion(question: Question) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.updateQuestion(question)
            _state.update { it.copy(isLoading = false, isUpdatingQuestion = false, isAddingQuestion = false, questionSelected = null) }
        }
    }

    fun getAllQuestions(): StateFlow<List<Question>> = repository.getAllQuestions().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    suspend fun getQuestionById(questionId: Int): Question? = repository.getQuestionById(questionId)

    fun getQuestionsByChapterId(chapterId: Int): StateFlow<List<Question>> =
        repository.getQuestionsByChapterId(chapterId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}