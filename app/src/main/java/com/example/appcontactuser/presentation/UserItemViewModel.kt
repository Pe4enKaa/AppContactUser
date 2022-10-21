package com.example.appcontactuser.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appcontactuser.data.repository.UserListRepositoryImpl
import com.example.appcontactuser.domain.AddUserItemUseCase
import com.example.appcontactuser.domain.EditUserItemUseCase
import com.example.appcontactuser.domain.GetUserItemUseCase
import com.example.appcontactuser.domain.UserItem
import kotlinx.coroutines.launch
import java.lang.Exception

class UserItemViewModel(application: Application) : AndroidViewModel(application)  {

    private val repository = UserListRepositoryImpl(application)

    private val getUserItem = GetUserItemUseCase(repository)
    private val addUserItem = AddUserItemUseCase(repository)
    private val editUserItem = EditUserItemUseCase(repository)

    private val _errorInputSurname = MutableLiveData<Boolean>()
    val errorInputSurname: LiveData<Boolean>
        get() = _errorInputSurname

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputPatronymic = MutableLiveData<Boolean>()
    val errorInputPatronymic: LiveData<Boolean>
        get() = _errorInputPatronymic

    private val _errorInputDataOfBirth = MutableLiveData<Boolean>()
    val errorInputDataOfBirth: LiveData<Boolean>
        get() = _errorInputDataOfBirth

    private val _errorInputEmail = MutableLiveData<Boolean>()
    val errorInputEmail: LiveData<Boolean>
        get() = _errorInputEmail

    private val _errorInputPhoneNumber = MutableLiveData<Boolean>()
    val errorInputPhoneNumber: LiveData<Boolean>
        get() = _errorInputPhoneNumber

    private val _userItem = MutableLiveData<UserItem>()
    val userItem: LiveData<UserItem>
        get() = _userItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(userItemId: Int) {
        viewModelScope.launch {
            val item = getUserItem.getUserItem(userItemId)
            _userItem.value = item
        }
    }

    fun addUserItem(
        inputSurname: String?,
        inputName: String?,
        inputPatronymic: String?,
        inputDataOfBirth: String?,
        inputEmail: String?,
        inputPhoneNumber: String?
    ) {

        val surname = parseUniqueString(inputSurname)
        val name = parseUniqueString(inputName)
        val patronymic = parseUniqueString(inputPatronymic)
        val dataOfBirth = parseUniqueString(inputDataOfBirth)
        val email = parseUniqueString(inputEmail)
        val phoneNumber = parsePhoneNumber(inputPhoneNumber)

        val fieldsValid = validateInput(
            surname,
            name,
            patronymic,
            dataOfBirth,
            email,
            phoneNumber)

        if (fieldsValid) {
            viewModelScope.launch {
                val shopItem = UserItem(
                    surname,
                    name,
                    patronymic,
                    dataOfBirth,
                    email,
                    phoneNumber)
                addUserItem.addUserItem(shopItem)
                finishWork()
            }
        }
    }

    fun editUserItem(inputSurname: String?,
                     inputName: String?,
                     inputPatronymic: String?,
                     inputDataOfBirth: String?,
                     inputEmail: String?,
                     inputPhoneNumber: String?) {

        val surname = parseUniqueString(inputSurname)
        val name = parseUniqueString(inputName)
        val patronymic = parseUniqueString(inputPatronymic)
        val dataOfBirth = parseUniqueString(inputDataOfBirth)
        val email = parseUniqueString(inputEmail)
        val phoneNumber = parsePhoneNumber(inputPhoneNumber)

        val fieldsValid = validateInput(
            surname,
            name,
            patronymic,
            dataOfBirth,
            email,
            phoneNumber)

        if (fieldsValid) {
            _userItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(
                        surname = surname,
                        name = name,
                        patronymic = patronymic,
                        dataOfBirth = dataOfBirth,
                        email = email,
                        phoneNumber = phoneNumber)
                    editUserItem.editUserItem(item)
                    finishWork()
                }
            }

        }
    }

    private fun parseUniqueString(parseUniqueString: String?): String {
        return parseUniqueString?.trim() ?: ""
    }

    private fun parsePhoneNumber(inputPhoneNumber: String?): Long {
        return try {
            inputPhoneNumber?.trim()?.toLong() ?: 0

        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(
        surname: String,
        name: String,
        patronymic: String,
        dataOfBirth: String,
        email: String,
        phoneNumber: Long
    ): Boolean {

        var result = true

        if (surname.isBlank()) {
            _errorInputName.value = true
            result = false
        }

        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }

        if (patronymic.isBlank()) {
            _errorInputName.value = true
            result = false
        }

        if (dataOfBirth.isBlank()) {
            _errorInputName.value = true
            result = false
        }

        if (email.isBlank()) {
            _errorInputName.value = true
            result = false
        }

        if (phoneNumber <= 0) {
            _errorInputPhoneNumber.value = true
            result = false
        }

        return result
    }

    fun resetErrorInputSurname() {
        _errorInputSurname.value = false
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputPatronymic() {
        _errorInputPatronymic.value = false
    }

    fun resetErrorInputDataOfBirth() {
        _errorInputDataOfBirth.value = false
    }

    fun resetErrorInputEmail() {
        _errorInputEmail.value = false
    }

    fun resetErrorInputPhoneNumber() {
        _errorInputPhoneNumber.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}