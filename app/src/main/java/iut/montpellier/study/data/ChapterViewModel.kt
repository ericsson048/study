package iut.montpellier.study.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iut.montpellier.study.data.entity.Chapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChapterState(
    val chapters: List<Chapter> = emptyList(),
    val isLoading: Boolean = false,
    val isAddingChapter: Boolean = false,
    val error: String? = null
)

sealed interface ChapterEvent {
    data class SaveChapter(val chapter: Chapter) : ChapterEvent
    data class DeleteChapter(val chapter: Chapter) : ChapterEvent
    data class UpdateChapter(val chapter: Chapter) : ChapterEvent
    data class OnShowDialog(val show: Boolean): ChapterEvent
    data class OnLoading(val load: Boolean): ChapterEvent
    object OnClearError: ChapterEvent
}

@HiltViewModel
class ChapterViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(ChapterState())
    val state = _state.asStateFlow()

    fun onEvent(event: ChapterEvent) {
        when (event) {
            is ChapterEvent.SaveChapter -> {
                insertChapter(event.chapter)
            }

            is ChapterEvent.DeleteChapter -> {
                deleteChapter(event.chapter)
            }

            is ChapterEvent.UpdateChapter -> {
                updateChapter(event.chapter)
            }
            is ChapterEvent.OnShowDialog -> {
                _state.update { it.copy(isAddingChapter = event.show) }
            }
            is ChapterEvent.OnLoading -> {
                _state.update { it.copy(isLoading = event.load) }
            }
            is ChapterEvent.OnClearError -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun insertChapter(chapter: Chapter) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.insertChapter(chapter)
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun deleteChapter(chapter: Chapter) {
        viewModelScope.launch { repository.deleteChapter(chapter) }
    }

    private fun updateChapter(chapter: Chapter) {
        viewModelScope.launch { repository.updateChapter(chapter) }
    }

    fun getAllChapters(): StateFlow<List<Chapter>> = repository.getAllChapters().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    suspend fun getChapterById(chapterId: Int): Chapter? = repository.getChapterById(chapterId)

    fun getChaptersByCourseId(courseId: Int): StateFlow<List<Chapter>> =
        repository.getChaptersByCourseId(courseId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}