package com.example.appcontactuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appcontactuser.domain.UserItem

@Dao
interface UserListDao {

    @Query("SELECT * FROM user_items")
    fun getUserList(): LiveData<List<UserItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserItem(userItemDbModel: UserItemDbModel)

    @Query("DELETE FROM user_items WHERE id=:userItemId")
    suspend fun deleteUserItem(userItemId: Int)

    @Query("SELECT * FROM user_items WHERE id=:userItemId LIMIT 1")
    suspend fun getShopItem(userItemId: Int): UserItemDbModel
}