package iut.montpellier.study.data


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iut.montpellier.study.data.entity.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private var _state = MutableStateFlow(CourseState())
    val allCourses= repository.getAllCourses().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    var state: StateFlow<CourseState> = combine(_state, allCourses) { state, courses ->
        state.copy(allCourses = courses)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CourseState()
    )

    fun insertCourse(course: Course) {
        viewModelScope.launch {
            repository.insertCourse(course)
            _state.value = _state.value.copy(
                isShowDialog = false,
                description = mutableStateOf(""),
                title = mutableStateOf(""),
            )
        }
    }
    fun onEvent(event: CourseEvent){
        when(event){
            is CourseEvent.AddCourse -> {
                insertCourse(event.course)
            }
            is CourseEvent.OnTitleChange -> {
                _state.value.title.value = event.title
            }
            is CourseEvent.OnDescriptionChange -> {
                _state.value.description.value = event.description
            }
            is CourseEvent.OnShowDialog -> {
                _state.value = _state.value.copy(isShowDialog = event.value)
            }
            CourseEvent.GetAllCourses -> TODO()
        }
    }

    fun deleteCourse(course: Course) {
        viewModelScope.launch { repository.deleteCourse(course) }
    }

    fun updateCourse(course: Course) {
        viewModelScope.launch { repository.updateCourse(course) }
    }
    suspend fun getCourseById(courseId:Int) : Course? = repository.getCourseById(courseId)
}

data class CourseState(
    val isShowDialog: Boolean = false,
    val loading: Boolean = false,
    var error: String = "",
    val allCourses: List<Course> = emptyList(), // allCourses
    val description: MutableState<String> = mutableStateOf(""),
    val title: MutableState<String> = mutableStateOf(""),
)