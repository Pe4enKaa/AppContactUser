package com.example.appcontactuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.appcontactuser.data.database.AppDatabase
import com.example.appcontactuser.data.mapper.UserListMapper
import com.example.appcontactuser.domain.UserItem
import com.example.appcontactuser.domain.UserListRepository

class UserListRepositoryImpl(application: Application): UserListRepository {

    private val userListDao = AppDatabase.getInstance(application).userListDao()
    private val mapper = UserListMapper()

    override suspend fun addUserItem(userItem: UserItem) {
        userListDao.addUserItem(mapper.mapEntityToDbModel(userItem))
    }

    override suspend fun deleteUserItem(userItem: UserItem) {
        userListDao.deleteUserItem(userItem.id)
    }

    override suspend fun editUserItem(userItem: UserItem) {
        userListDao.addUserItem(mapper.mapEntityToDbModel(userItem))
    }

    override suspend fun getUserItem(userItemId: Int): UserItem {
        val dbModel = userListDao.getUserItem(userItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getUserList(): LiveData<List<UserItem>> = Transformations.map(
        userListDao.getUserList()
    ) {
        mapper.mapListDbModelToListEntity(it)
    }
}