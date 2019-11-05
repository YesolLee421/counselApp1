package com.example.counselapp.adapter

import com.example.counselapp.model.User

interface SearchExpertAdapterContract{

    // View는 다른 어댑터와 동일

    interface Model{
        fun addItems(userList: List<User>)
        fun clearItems()
        fun getItem(position: Int): User
    }


}