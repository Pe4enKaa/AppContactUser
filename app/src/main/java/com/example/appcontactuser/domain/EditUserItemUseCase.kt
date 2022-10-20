package com.example.appcontactuser.domain

class EditUserItemUseCase(private val userListRepository: UserListRepository) {

    suspend fun editUserItem(userItem: UserItem) {
        userListRepository.editUserItem(userItem)
    }
}