package iut.montpellier.study.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iut.montpellier.study.data.entity.Progress
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    fun insertProgress(progress: Progress) {
        viewModelScope.launch { repository.insertProgress(progress) }
    }

    fun deleteProgress(progress: Progress) {
        viewModelScope.launch { repository.deleteProgress(progress) }
    }

    fun updateProgress(progress: Progress) {
        viewModelScope.launch { repository.updateProgress(progress) }
    }

    fun getAllProgress(): StateFlow<List<Progress>> = repository.getAllProgress().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    suspend fun getProgressById(progressId: Int): Progress? = repository.getProgressById(progressId)
    fun getProgressByUserId(userId: Int): StateFlow<List<Progress>> = repository.getProgressByUserId(userId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    fun getProgressByChapterId(chapterId: Int): StateFlow<List<Progress>> = repository.getProgressByChapterId(chapterId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    suspend fun getProgressByUserIdAndChapterId(userId: Int, chapterId: Int): Progress? = repository.getProgressByUserIdAndChapterId(userId, chapterId)
}