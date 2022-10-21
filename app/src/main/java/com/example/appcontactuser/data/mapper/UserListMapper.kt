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
        phoneNumber = userItem.phoneNumber
    )

    fun mapDbModelToEntity(userItemDbModel: UserItemDbModel): UserItem = UserItem(
        id = userItemDbModel.id,
        surname = userItemDbModel.surname,
        name = userItemDbModel.name,
        patronymic = userItemDbModel.patronymic,
        dataOfBirth = userItemDbModel.dataOfBirth,
        email = userItemDbModel.email,
        phoneNumber = userItemDbModel.phoneNumber
    )

    fun mapListDbModelToListEntity(list: List<UserItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}