package iut.montpellier.study.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import iut.montpellier.study.data.entity.Chapter
import iut.montpellier.study.data.entity.Course
import iut.montpellier.study.data.entity.Progress
import iut.montpellier.study.data.entity.Question
import iut.montpellier.study.data.entity.Users

@Database(
    entities = [Users::class, Course::class, Chapter::class, Question::class, Progress::class],
    version = 2,
    exportSchema = false
)
abstract class StudyDatabase : RoomDatabase() {

    abstract fun userDao(): userDao
    abstract fun courseDao(): CourseDao
    abstract fun chapterDao(): ChapterDao
    abstract fun questionDao(): QuestionDao
    abstract fun progressDao(): ProgressDao

    companion object {
        var db: StudyDatabase? = null
    }
}