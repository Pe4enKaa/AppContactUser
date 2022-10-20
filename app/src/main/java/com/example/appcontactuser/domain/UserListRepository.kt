package com.example.appcontactuser.domain

import androidx.lifecycle.LiveData

interface UserListRepository {

    suspend fun addUserItem(userItem: UserItem)

    suspend fun deleteUserItem(userItem: UserItem)

    suspend fun editUserItem(userItem: UserItem)

    suspend fun getUserItem(userItemId: Int): UserItem

    fun getUserList(): LiveData<List<UserItem>>

}