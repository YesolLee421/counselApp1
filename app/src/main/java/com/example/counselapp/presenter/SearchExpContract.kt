package com.example.counselapp.presenter

import android.content.Context
import com.example.counselapp.adapter.MainAdapterContract
import com.example.counselapp.adapter.SearchExpertAdapterContract
import com.example.counselapp.model.User

interface SearchExpContract {
    // interface View는 MainContract꺼 사용

    interface Presenter {
        var view: MainboardContract.View
        //var userList: ArrayList<User>
        var adapterModel: SearchExpertAdapterContract.Model
        var adapterView: MainAdapterContract.View?

        fun loadItems(context: Context, isClear: Boolean)
    }
}