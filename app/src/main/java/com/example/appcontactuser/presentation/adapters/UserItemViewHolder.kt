package com.example.appcontactuser.presentation.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcontactuser.R

class UserItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val tvName: TextView = view.findViewById(R.id.tv_name)
    val tvPhoneNumber: TextView = view.findViewById(R.id.tv_phone_number)
}