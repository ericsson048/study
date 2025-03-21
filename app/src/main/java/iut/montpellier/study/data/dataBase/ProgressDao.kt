package iut.montpellier.study.data.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import iut.montpellier.study.data.entity.Progress
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: Progress)

    @Delete
    suspend fun deleteProgress(progress: Progress)

    @Update
    suspend fun updateProgress(progress: Progress)

    @Query("SELECT * FROM Progress")
    fun getAllProgress(): Flow<List<Progress>>

    @Query("SELECT * FROM Progress WHERE id = :progressId")
    suspend fun getProgressById(progressId: Int): Progress?

    @Query("SELECT * FROM Progress WHERE userId = :userId")
    fun getProgressByUserId(userId: Int): Flow<List<Progress>>

    @Query("SELECT * FROM Progress WHERE chapterId = :chapterId")
    fun getProgressByChapterId(chapterId: Int): Flow<List<Progress>>

    @Query("SELECT * FROM Progress WHERE userId = :userId AND chapterId = :chapterId")
    suspend fun getProgressByUserIdAndChapterId(userId: Int, chapterId: Int): Progress?
}