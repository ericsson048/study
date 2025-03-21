package iut.montpellier.study.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import iut.montpellier.study.data.Repository
import iut.montpellier.study.data.dataBase.StudyDatabase
import iut.montpellier.study.data.dataBase.Migrations // This should work now!
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    const val DATABASE_NAME = "study_db" // Use a constant for the database name

    @Provides
    @Singleton
    fun provideStudyDatabase(
        @ApplicationContext context: Context
    ): StudyDatabase {
        return Room.databaseBuilder(
            context,
            StudyDatabase::class.java,
            DATABASE_NAME
        ).addMigrations(Migrations.MIGRATION_1_2) // Add the migration here
            .build()
    }

    @Provides
    @Singleton
    fun provideRepository(database: StudyDatabase): Repository {
        return Repository(
            userDao = database.userDao(),
            courseDao = database.courseDao(),
            chapterDao = database.chapterDao(),
            questionDao = database.questionDao(),
            progressDao = database.progressDao()
        )
    }
}