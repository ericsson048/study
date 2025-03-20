package iut.montpellier.study.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import iut.montpellier.study.data.entity.Users

@Database(entities = [Users::class], version = 1, exportSchema = false)
abstract class UserDataBase:RoomDatabase() {
    abstract  fun userDao():userDao

    companion object{
        var db:UserDataBase? =null
    }
}