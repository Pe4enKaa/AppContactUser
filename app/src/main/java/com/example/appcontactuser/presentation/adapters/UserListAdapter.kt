package com.example.appcontactuser.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.appcontactuser.R
import com.example.appcontactuser.domain.UserItem

class UserListAdapter: ListAdapter<UserItem, UserItemViewHolder>(UserItemDiffCallback()) {

    var onUserItemClickListener: ((UserItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_user,
            parent,
            false
        )
        return UserItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        val userItem = getItem(position)

        holder.itemView.setOnClickListener {
            onUserItemClickListener?.invoke(userItem)
        }

        holder.tvName.text = userItem.name
        holder.tvPhoneNumber.text = userItem.phoneNumber
    }

    companion object{
        const val MAX_POOL_SIZE = 30
    }
}