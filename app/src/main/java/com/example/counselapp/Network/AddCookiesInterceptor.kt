package com.example.counselapp.Network

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AddCookiesInterceptor//생성자
    (internal var context: Context) : Interceptor {
    var TAG = "AddCookiesInterceptor"
    internal lateinit var sharedPreference: SharedPreferences

    override fun intercept(chain: Interceptor.Chain): Response {
        sharedPreference = context.getSharedPreferences("UserSign",Context.MODE_PRIVATE)
        val builder = chain.request().newBuilder()
        //sharedPreference에서 cookie 가져오기
        builder.addHeader("Cookie", "connect.sid="+sharedPreference.getString("Cookie","")!!)
        builder.addHeader("Content-Type","application/json")

        return chain.proceed(builder.build())
    }
}