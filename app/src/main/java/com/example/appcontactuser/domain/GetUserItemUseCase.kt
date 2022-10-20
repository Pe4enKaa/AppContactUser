package com.example.appcontactuser.domain

class GetUserItemUseCase(private val userListRepository: UserListRepository) {

    suspend fun getUserItem(userItemId: Int): UserItem {
        return userListRepository.getUserItem(userItemId)
    }
}