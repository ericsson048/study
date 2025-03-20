package iut.montpellier.study.data

import iut.montpellier.study.data.dataBase.userDao
import iut.montpellier.study.data.entity.Users

class Repository(private  val userDao: userDao) {

    suspend fun insertUser(user: Users) = userDao.insertUser(user)
    suspend fun loginUser(user: Users) = userDao.loginUser(email = user.email, password = user.password)
    suspend fun updateUser(user: Users) = userDao.updateUser(user)
    suspend fun deleteUser(user: Users) = userDao.deleteUser(user)
    fun getAllUsers() = userDao.getAllUsers()
    suspend fun getUserByEmail(user: Users)=userDao.getUserByEmail(email = user.email)



}