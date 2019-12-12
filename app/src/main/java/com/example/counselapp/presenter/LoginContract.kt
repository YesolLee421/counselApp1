package com.example.counselapp.presenter

import com.example.counselapp.base.BasePresenter
import com.example.counselapp.base.BaseView
import com.example.counselapp.model.User
import com.example.counselapp.Network.CounselAppService
import io.reactivex.disposables.CompositeDisposable

interface LoginContract {
    interface View : BaseView{
        fun showLoading()
        fun hideLoading()
        fun checkUserList(userList : List<User>)
        fun showToast(msg: String)
        fun moveTo()
    }

    interface Presenter : BasePresenter<View>{
        //var userData : ArrayList<User>

        var presenterService: CounselAppService
        var presenterCompositeDisposable: CompositeDisposable
        //fun getUserList()
        fun doLogin(id: String, pw: String)
    }
}