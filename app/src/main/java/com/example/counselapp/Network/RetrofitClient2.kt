package com.example.counselapp.Network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient2 {

    // 로그
    val logginInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    //retrofit에 담을 client 종류 세 가지 중 하나 얻어오기
    fun getClient(context: Context, type: String): OkHttpClient{
        // 1. 저장된 쿠키 헤더에 추가할 때
        if(type.equals("addCookies")){
            val addCookiesInterceptor = AddCookiesInterceptor(context)
            val client = OkHttpClient.Builder()
                .addInterceptor(addCookiesInterceptor)
                .addInterceptor(logginInterceptor)
                .build()
            return client
        } // 2. 쿠키 받아서 저장할 때
        else if(type.equals("receiveCookies")){
            val receivedCookiesInterceptor = ReceivedCookiesInterceptor(context)
            val client = OkHttpClient.Builder()
                .addInterceptor(receivedCookiesInterceptor)
                .addInterceptor(logginInterceptor)
                .build()
            return client
        } // 3. 기타 쿠키 필요 없는 경우 ex) 회원가입
        else {
            val client = OkHttpClient.Builder()
                .addInterceptor(logginInterceptor)
                .build()
            return client
        }
    }

    //retrofit2 선언
    fun retrofit(baseUrl: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    fun serviceAPI(client: OkHttpClient): CounselAppService = retrofit("http://10.0.2.2:3000/", client).create(CounselAppService::class.java)
}