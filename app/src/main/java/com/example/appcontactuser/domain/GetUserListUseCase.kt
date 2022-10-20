package com.example.appcontactuser.domain

import androidx.lifecycle.LiveData

class GetUserListUseCase(private val userListRepository: UserListRepository) {

    fun getUserList(): LiveData<List<UserItem>> {
        return userListRepository.getUserList()
    }
}