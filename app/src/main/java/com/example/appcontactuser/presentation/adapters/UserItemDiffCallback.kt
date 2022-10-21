package com.example.appcontactuser.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.appcontactuser.domain.UserItem

class UserItemDiffCallback: DiffUtil.ItemCallback<UserItem>() {

    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem == newItem
    }
}