package com.example.counselapp.adapter

import com.example.counselapp.Model.Post

interface mainAdapterContract {

    interface View{
        fun notifyAdapter()
    }

    interface Model{
        fun addItems(postList: ArrayList<Post>)
        fun clearItems()
    }
}