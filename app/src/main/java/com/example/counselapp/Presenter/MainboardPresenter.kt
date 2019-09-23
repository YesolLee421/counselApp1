package com.example.counselapp.Presenter

import android.content.Context
import com.example.counselapp.Model.Post
import com.example.counselapp.Model.PostList
import com.example.counselapp.adapter.mainAdapterContract

class MainboardPresenter :MainboardContract.Presenter{


    lateinit override var view: MainboardContract.View

    lateinit override var adapterModel: mainAdapterContract.Model
    lateinit override var adapterView: mainAdapterContract.View

    lateinit override var postList: PostList

    override fun loadItems(context: Context, isClear: Boolean) {
        postList.getPostList(context,PostList.postData.size).let {
            if(isClear){
                adapterModel.clearItems()
            }
            adapterModel.addItems(it)
            adapterView.notifyAdapter()
        }

    }








}