package iut.montpellier.study.data.dataBase

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Create the Course table
            database.execSQL("CREATE TABLE IF NOT EXISTS `Courses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL)")

            // Add the courseId column to Chapters table
            database.execSQL("ALTER TABLE `Chapters` ADD COLUMN `courseId` INTEGER NOT NULL DEFAULT 0")
        }
    }
}