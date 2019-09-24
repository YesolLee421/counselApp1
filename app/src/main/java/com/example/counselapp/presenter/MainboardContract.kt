package com.example.counselapp.presenter

import android.content.Context
import com.example.counselapp.model.PostList
import com.example.counselapp.adapter.MainAdapterContract
import com.example.counselapp.model.Post

interface MainboardContract {
    interface View {
        fun showToast(title: String)
    }

    interface Presenter{
        var view: View
        var postList: ArrayList<Post>
        // takeView(), dropView() : BasePresenter에 있음

        var adapterModel: MainAdapterContract.Model
        var adapterView: MainAdapterContract.View?

        fun loadItems(context: Context, isClear: Boolean)





    }
}