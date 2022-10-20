package com.example.appcontactuser.domain

class DeleteUserItemUseCase(private val userListRepository: UserListRepository) {

    suspend fun deleteUserItem(userItem: UserItem) {
        userListRepository.deleteUserItem(userItem)
    }
}