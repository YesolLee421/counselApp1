package com.example.counselapp.presenter

import android.content.Context
import com.example.counselapp.model.PostList
import com.example.counselapp.adapter.MainAdapterContract
import com.example.counselapp.model.Post

class MainboardPresenter :MainboardContract.Presenter{

    override lateinit var view: MainboardContract.View

    // 여기서 선언하기 때문에 takeView(), dropView()필요없음
    override lateinit var adapterModel: MainAdapterContract.Model
    override var adapterView: MainAdapterContract.View? = null
        set(value) {
            field = value
            field?.onClickFunc = {onClickListener(it)}
        }

    private fun onClickListener(position: Int){
        adapterModel.getItem(position).let {
            view.showToast(it.title)
        }
    }

    override var postList: ArrayList<Post> = PostList.getPostList(PostList.postData.size)
    
    override fun loadItems(context: Context, isClear: Boolean) {
        postList.let {
            if(isClear){
                adapterModel.clearItems()
            }
            adapterModel.addItems(it)
            adapterView?.notifyAdapter() // presenter에서 어댑터 직접 접근, view, model 접근
        }

    }








}