package iut.montpellier.study.data

import iut.montpellier.study.data.dataBase.ChapterDao
import iut.montpellier.study.data.dataBase.CourseDao
import iut.montpellier.study.data.dataBase.ProgressDao
import iut.montpellier.study.data.dataBase.QuestionDao
import iut.montpellier.study.data.dataBase.userDao
import iut.montpellier.study.data.entity.Chapter
import iut.montpellier.study.data.entity.Course
import iut.montpellier.study.data.entity.Progress
import iut.montpellier.study.data.entity.Question
import iut.montpellier.study.data.entity.Users
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val userDao: userDao,
    private val courseDao: CourseDao,
    private val chapterDao: ChapterDao,
    private val questionDao: QuestionDao,
    private val progressDao: ProgressDao
) {
    // User operations
    suspend fun insertUser(user: Users) = userDao.insertUser(user)
    suspend fun loginUser(user: Users) =
        userDao.loginUser(email = user.email, password = user.password)

    suspend fun updateUser(user: Users) = userDao.updateUser(user)
    suspend fun deleteUser(user: Users) = userDao.deleteUser(user)
    fun getAllUsers(): Flow<List<Users>> = userDao.getAllUsers()
    suspend fun getUserByEmail(email: String) = userDao.getUserByEmail(email = email)
    suspend fun isUserValid(email: String, password: String): Boolean {
        return userDao.isUserValid(email, password)
    }

    // Course operations
    suspend fun insertCourse(course: Course) = courseDao.insertCourse(course)
    suspend fun deleteCourse(course: Course) = courseDao.deleteCourse(course)
    suspend fun updateCourse(course: Course) = courseDao.updateCourse(course)
    fun getAllCourses(): Flow<List<Course>> = courseDao.getAllCourses()
    suspend fun getCourseById(courseId: Int): Course? = courseDao.getCourseById(courseId)

    // Chapter operations
    suspend fun insertChapter(chapter: Chapter) = chapterDao.insertChapter(chapter)
    suspend fun deleteChapter(chapter: Chapter) = chapterDao.deleteChapter(chapter)
    suspend fun updateChapter(chapter: Chapter) = chapterDao.updateChapter(chapter)
    fun getAllChapters(): Flow<List<Chapter>> = chapterDao.getAllChapters()
    suspend fun getChapterById(chapterId: Int): Chapter? = chapterDao.getChapterById(chapterId)
    fun getChaptersByCourseId(courseId: Int): Flow<List<Chapter>> =
        chapterDao.getChaptersByCourseId(courseId)

    // Question operations
    suspend fun insertQuestion(question: Question) = questionDao.insertQuestion(question)
    suspend fun deleteQuestion(question: Question) = questionDao.deleteQuestion(question)
    suspend fun updateQuestion(question: Question) = questionDao.updateQuestion(question)
    fun getAllQuestions(): Flow<List<Question>> = questionDao.getAllQuestions()
    suspend fun getQuestionById(questionId: Int): Question? =
        questionDao.getQuestionById(questionId)

    fun getQuestionsByChapterId(chapterId: Int): Flow<List<Question>> =
        questionDao.getQuestionsByChapterId(chapterId)

    // Progress operations
    suspend fun insertProgress(progress: Progress) = progressDao.insertProgress(progress)
    suspend fun deleteProgress(progress: Progress) = progressDao.deleteProgress(progress)
    suspend fun updateProgress(progress: Progress) = progressDao.updateProgress(progress)
    fun getAllProgress(): Flow<List<Progress>> = progressDao.getAllProgress()
    suspend fun getProgressById(progressId: Int): Progress? =
        progressDao.getProgressById(progressId)

    fun getProgressByUserId(userId: Int): Flow<List<Progress>> =
        progressDao.getProgressByUserId(userId)

    fun getProgressByChapterId(chapterId: Int): Flow<List<Progress>> =
        progressDao.getProgressByChapterId(chapterId)

    suspend fun getProgressByUserIdAndChapterId(userId: Int, chapterId: Int): Progress? =
        progressDao.getProgressByUserIdAndChapterId(userId, chapterId)
}