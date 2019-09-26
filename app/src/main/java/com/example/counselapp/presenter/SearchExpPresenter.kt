package com.example.counselapp.presenter

import android.content.Context
import com.example.counselapp.adapter.MainAdapterContract
import com.example.counselapp.adapter.SearchExpertAdapterContract
import com.example.counselapp.model.User
import com.example.counselapp.model.UserList

class SearchExpPresenter: SearchExpContract.Presenter {
    override lateinit var view: MainboardContract.View
    override var userList: ArrayList<User> = UserList.getUserList()
    override lateinit var adapterModel: SearchExpertAdapterContract.Model
    override var adapterView: MainAdapterContract.View? = null
        set(value) {
            field = value
            field?.onClickFunc = {onClickListener(it)}
        }
    private fun onClickListener(position: Int){
        adapterModel.getItem(position).let {
            view.showToast(it.name)
        }
    }

    var USER_NORMAL :Int = 0
    var USER_EXPERT :Int = 1

    override fun loadItems(context: Context, isClear: Boolean) {
        userList.let {
            if(isClear){
                adapterModel.clearItems()
            }
            // 상담사인 것만 추가하기: 일반 사용자 삭제
            for(i in userList.size until 0){
                if(userList[i].type==USER_NORMAL){
                    userList.removeAt(i) // arraylist에서 삭제할 때 인덱스 주의
                }
            }
            adapterModel.addItems(it)
            adapterView?.notifyAdapter()
        }
    }
}