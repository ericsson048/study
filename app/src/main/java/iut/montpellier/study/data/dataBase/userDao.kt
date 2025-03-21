package iut.montpellier.study.data.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import iut.montpellier.study.data.entity.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface userDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: Users)

    @Delete
    suspend fun deleteUser(users: Users)

    @Update
    suspend fun updateUser(users: Users)

    @Query("SELECT * FROM Users")
    fun getAllUsers():Flow<List<Users>>

    @Query("SELECT * FROM Users WHERE email = :email AND password = :password")
    fun loginUser(email: String, password: String): Users?

    @Query("SELECT * FROM Users WHERE email = :email")
    suspend fun getUserByEmail(email: String): Users?

    @Query("SELECT EXISTS(SELECT 1 FROM Users WHERE email = :email AND password = :password)")
    suspend fun isUserValid(email: String, password: String): Boolean

}