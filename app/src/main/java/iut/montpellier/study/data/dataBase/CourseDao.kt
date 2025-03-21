package iut.montpellier.study.data.dataBase


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import iut.montpellier.study.data.entity.Course
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: Course)

    @Delete
    suspend fun deleteCourse(course: Course)

    @Update
    suspend fun updateCourse(course: Course)

    @Query("SELECT * FROM Courses")
    fun getAllCourses(): Flow<List<Course>>

    @Query("SELECT * FROM Courses WHERE id = :courseId")
    suspend fun getCourseById(courseId: Int): Course?
}