package com.example.counselapp.presenter

import android.content.Context
import android.util.Log
import com.example.counselapp.adapter.MainAdapterContract
import com.example.counselapp.adapter.SearchExpertAdapterContract
import com.example.counselapp.model.Expert
import com.example.counselapp.model.Post
import com.example.counselapp.model.User
import com.example.counselapp.model.UserList
import com.example.counselapp.retrofit.CounselAppService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchExpPresenter: SearchExpContract.Presenter {
    var TAG = "SearchExpPresenter"

    override lateinit var presenterService: CounselAppService
    override lateinit var expertList: List<Expert>
    override lateinit var view: MainboardContract.View

    override lateinit var adapterModel: SearchExpertAdapterContract.Model
    override var adapterView: MainAdapterContract.View? = null
        set(value) {
            field = value
            field?.onClickFunc = {onClickListener(it)}
        }
    private fun onClickListener(position: Int){
        adapterModel.getItem(position).let {
            view.showToast(it.name_formal)
            Log.d(TAG, it.name_formal)
            view.moveTo(it._id)
        }
    }

    var USER_NORMAL :Int = 0
    var USER_EXPERT :Int = 1

    override fun loadItems(context: Context, isClear: Boolean) {
        val call = presenterService.getAllExperts()
        call.enqueue(object : Callback<List<Expert>> {
            override fun onFailure(call: Call<List<Expert>>, t: Throwable) {
                Log.d(TAG,"onFailure: ${t.message}")
                view.showToast(t.message.toString());
            }
            override fun onResponse(call: Call<List<Expert>>, response: Response<List<Expert>>) {
                if(response.code()==200){
                    expertList = response.body()!!
                    expertList.let {
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