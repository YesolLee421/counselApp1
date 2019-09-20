package com.example.counselapp.Base

interface BasePresenter<T> {
    // view 객체 받아야되니 <T> 적어주기
    fun takeView(view: T)
    fun dropView()
}