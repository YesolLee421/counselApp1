package com.example.counselapp.presenter

import com.example.counselapp.base.BasePresenter
import com.example.counselapp.base.BaseView
import com.example.counselapp.model.User

interface LoginContract {
    interface View : BaseView{
        fun showLoading()
        fun hideLoading()
        fun checkUserList(userList : List<User>)
    }

    interface Presenter : BasePresenter<View>{
        fun getUserList()
        fun doLogin(id: String, pw: String): Int
    }
}