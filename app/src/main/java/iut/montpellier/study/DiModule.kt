package iut.montpellier.study

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iut.montpellier.study.data.Repository
import iut.montpellier.study.data.dataBase.UserDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    @Singleton
    fun provideDataBase(application: Application):UserDataBase{
        return Room.databaseBuilder(application,
            UserDataBase::class.java,
            "user_database.sql").build()

    }

    @Provides
    @Singleton
    fun provideRepository(database:UserDataBase):Repository{
        return Repository(database.userDao())
    }
}