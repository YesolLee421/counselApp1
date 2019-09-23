package com.example.counselapp.Presenter

import android.content.Context
import com.example.counselapp.Model.Post
import com.example.counselapp.Model.PostList
import com.example.counselapp.adapter.mainAdapterContract

interface MainboardContract {
    interface View

    interface Presenter {
        var view: MainboardContract.View
        var postList: PostList

        var adapterModel: mainAdapterContract.Model
        var adapterView: mainAdapterContract.View

        fun loadItems(context: Context, isClear: Boolean)


    }
}