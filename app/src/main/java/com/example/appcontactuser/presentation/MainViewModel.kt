package com.example.appcontactuser.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcontactuser.data.repository.UserListRepositoryImpl
import com.example.appcontactuser.domain.DeleteUserItemUseCase
import com.example.appcontactuser.domain.GetUserListUseCase
import com.example.appcontactuser.domain.UserItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = UserListRepositoryImpl(application)

    private val getUserList = GetUserListUseCase(repository)

    private val deleteUserList = DeleteUserItemUseCase(repository)

    val userList = getUserList.getUserList()

    fun deleteUserList(userItem: UserItem) {
        viewModelScope.launch {
            deleteUserList.deleteUserItem(userItem)
        }
    }
}