package com.example.counselapp.presenter

import com.example.counselapp.base.BasePresenter
import com.example.counselapp.base.BaseView
import com.example.counselapp.model.User
import com.example.counselapp.model.UserList

interface LoginContract {
    interface View : BaseView{
        fun showLoading()
        fun hideLoading()
        fun checkUserList(userList : List<User>)
    }

    interface Presenter : BasePresenter<View>{
        var userData : ArrayList<User>
        fun getUserList()
        fun doLogin(id: String, pw: String): Int
    }
}