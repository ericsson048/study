package iut.montpellier.study.data.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import iut.montpellier.study.data.entity.Chapter
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(chapter: Chapter)

    @Delete
    suspend fun deleteChapter(chapter: Chapter)

    @Update
    suspend fun updateChapter(chapter: Chapter)

    @Query("SELECT * FROM Chapters")
    fun getAllChapters(): Flow<List<Chapter>>

    @Query("SELECT * FROM Chapters WHERE id = :chapterId")
    suspend fun getChapterById(chapterId: Int): Chapter?

    @Query("SELECT * FROM Chapters WHERE courseId = :courseId")
    fun getChaptersByCourseId(courseId: Int): Flow<List<Chapter>>
}