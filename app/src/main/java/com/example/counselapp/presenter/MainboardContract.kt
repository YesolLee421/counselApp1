package com.example.counselapp.presenter

import android.content.Context
import com.example.counselapp.adapter.MainAdapterContract
import com.example.counselapp.model.Post
import com.example.counselapp.Network.CounselAppService

interface MainboardContract {
    interface View {
        fun showToast(message: String)
        fun moveTo(_id: String?, type: String)
    }

    interface Presenter{
        var presenterService: CounselAppService
        var view: View
        var postList: List<Post>
        // takeView(), dropView() : BasePresenter에 있음

        var adapterModel: MainAdapterContract.Model
        var adapterView: MainAdapterContract.View?

        fun loadItems(context: Context, isClear: Boolean)
        fun doLogout()

    }
}