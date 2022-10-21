package com.example.appcontactuser.domain


data class UserItem(
    val surname: String,
    val name: String,
    val patronymic : String,
    val dataOfBirth : String,
    val email : String,
    val phoneNumber : Long,
    val id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
