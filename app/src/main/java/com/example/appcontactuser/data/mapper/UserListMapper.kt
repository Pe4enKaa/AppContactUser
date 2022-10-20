package com.example.appcontactuser.data.mapper

import com.example.appcontactuser.data.database.UserItemDbModel
import com.example.appcontactuser.domain.UserItem

class UserListMapper {

    fun mapEntityToDbModel(userItem: UserItem): UserItemDbModel = UserItemDbModel(
        id = userItem.id,
        surname = userItem.surname,
        name = userItem.name,
        patronymic = userItem.patronymic,
        dataOfBirth = userItem.dataOfBirth,
        email = userItem.email,
        phoneNumber = userItem.phoneNumber,
        login = userItem.login,
        password = userItem.password
    )

    fun mapDbModelToEntity(userItemDbModel: UserItemDbModel): UserItem = UserItem(
        id = userItemDbModel.id,
        surname = userItemDbModel.surname,
        name = userItemDbModel.name,
        patronymic = userItemDbModel.patronymic,
        dataOfBirth = userItemDbModel.dataOfBirth,
        email = userItemDbModel.email,
        phoneNumber = userItemDbModel.phoneNumber,
        login = userItemDbModel.login,
        password = userItemDbModel.password
    )

    fun mapListDbModelToListEntity(list: List<UserItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}