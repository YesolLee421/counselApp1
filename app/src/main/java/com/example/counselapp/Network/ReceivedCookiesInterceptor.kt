package com.example.counselapp.Network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookiesInterceptor//생성자
    (internal var context: Context) : Interceptor{
    var TAG = "ReceivedCookiesInterceptor"
    internal lateinit var sharedPreference: SharedPreferences

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) { // header에 쿠키값 있을 때
            val cookies = HashSet<String>() // 쿠키 값 받기
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
            Log.e(TAG, cookies.toString())

            // Preference에 받은 cookies를 넣어주기
            val arr = cookies.toString().split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val arr2 = arr[0].split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            Log.e(TAG, arr2[1])
            sharedPreference = context.getSharedPreferences("UserSign", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putString("Cookie", arr2[1])
            editor.apply()
        }
        return originalResponse
    }
}