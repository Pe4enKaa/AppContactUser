package com.example.appcontactuser.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_items")
data class UserItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val surname: String,
    val name: String,
    val patronymic : String,
    val dataOfBirth : String,
    val email : String,
    val phoneNumber : Long,
)
