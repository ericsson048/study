package iut.montpellier.study.data.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import iut.montpellier.study.data.entity.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question)

    @Delete
    suspend fun deleteQuestion(question: Question)

    @Update
    suspend fun updateQuestion(question: Question)

    @Query("SELECT * FROM Questions")
    fun getAllQuestions(): Flow<List<Question>>

    @Query("SELECT * FROM Questions WHERE id = :questionId")
    suspend fun getQuestionById(questionId: Int): Question?

    @Query("SELECT * FROM Questions WHERE chapterId = :chapterId")
    fun getQuestionsByChapterId(chapterId: Int): Flow<List<Question>>
}