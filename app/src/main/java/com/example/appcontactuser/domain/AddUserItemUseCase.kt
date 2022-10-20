package com.example.appcontactuser.domain

class AddUserItemUseCase(private val userListRepository: UserListRepository) {

    suspend fun addUserItem(userItem: UserItem) {
        userListRepository.addUserItem(userItem)
    }
}