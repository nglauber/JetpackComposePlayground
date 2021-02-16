package br.com.nglauber.jetpacksample.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalRepository(context: Context) {
    private val userDao = AppDatabase.getInstance(context).userDao()

    fun getAll(): Flow<List<User>> =
        userDao.getAll().map { userEntityList ->
            userEntityList.map { it.toUser() }
        }

    fun getUser(id: Long): Flow<User?> =
        userDao.getUser(id).map { userEntity ->
            userEntity?.toUser()
        }

    suspend fun insert(user: User): Long = userDao.insert(UserEntity.fromUser(user))

    suspend fun update(user: User): Int = userDao.update(UserEntity.fromUser(user))

    suspend fun delete(user: User): Int = userDao.delete(UserEntity.fromUser(user))
}