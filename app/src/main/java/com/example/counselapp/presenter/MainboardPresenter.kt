package com.example.counselapp.presenter

import android.content.Context
import android.util.Log
import com.example.counselapp.adapter.MainAdapterContract
import com.example.counselapp.model.Post
import com.example.counselapp.retrofit.CounselAppService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainboardPresenter :MainboardContract.Presenter{
    var TAG = "LoginPresenter"
    override lateinit var view: MainboardContract.View
    override lateinit var presenterService: CounselAppService

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
            Log.d(TAG,it._id)
            view.moveTo(it._id)
        }
    }

    override lateinit var postList: List<Post>
    
    override fun loadItems(context: Context, isClear: Boolean) {
        val call = presenterService.getAllPosts()
        call.enqueue(object : Callback<List<Post>>{
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d(TAG,"onFailure: ${t.message}")
                view.showToast(t.message.toString());
            }
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if(response.code()==200){
                    postList = response.body()!!
                    postList.let {
                        if(isClear){
                            adapterModel.clearItems()
                        }
                        adapterModel.addItems(it)
                        adapterView?.notifyAdapter() // presenter에서 어댑터 직접 접근, view, model 접근
                    }
                    Log.d(TAG,"onResponse: 성공")
                    //view.showToast(response.body().toString())
                }
            }
        })
    }
}