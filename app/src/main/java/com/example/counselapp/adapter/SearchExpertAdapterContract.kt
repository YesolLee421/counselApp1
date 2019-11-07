package com.example.counselapp.adapter

import com.example.counselapp.model.Expert
import com.example.counselapp.model.User

interface SearchExpertAdapterContract{

    // View는 다른 어댑터와 동일

    interface Model{
        fun addItems(expertList: List<Expert>)
        fun clearItems()
        fun getItem(position: Int): Expert
    }


}