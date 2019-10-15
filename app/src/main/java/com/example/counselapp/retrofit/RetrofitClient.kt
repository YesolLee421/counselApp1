package com.example.counselapp.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var OurInstance: Retrofit? = null
    val instance: Retrofit
        get() {
            if(OurInstance==null){
                OurInstance = Retrofit.Builder()
                    .baseUrl("http://10.0.0.2:4000") // 에뮬에서 localhost
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return OurInstance!!
        }
}