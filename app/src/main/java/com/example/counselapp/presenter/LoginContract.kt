package com.example.counselapp.presenter

import com.example.counselapp.base.BasePresenter
import com.example.counselapp.base.BaseView
import com.example.counselapp.model.User
import com.example.counselapp.model.UserList
import com.example.counselapp.retrofit.CounselAppService
import io.reactivex.disposables.CompositeDisposable

interface LoginContract {
    interface View : BaseView{
        fun showLoading()
        fun hideLoading()
        fun checkUserList(userList : List<User>)
    }

    interface Presenter : BasePresenter<View>{
        var userData : ArrayList<User>
        fun getUserList()
        fun doLogin(id: String, pw: String, service: CounselAppService, compositeDisposable: CompositeDisposable): String
    }
}