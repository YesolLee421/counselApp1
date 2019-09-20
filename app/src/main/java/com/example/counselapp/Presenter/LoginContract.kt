package com.example.counselapp.Presenter

import com.example.counselapp.Base.BasePresenter
import com.example.counselapp.Base.BaseView
import com.example.counselapp.Model.User

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