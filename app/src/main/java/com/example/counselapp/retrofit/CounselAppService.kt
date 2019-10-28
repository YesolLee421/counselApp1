package com.example.counselapp.retrofit

import com.example.counselapp.model.User
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface CounselAppService {

    @POST("/auth/register")
    @FormUrlEncoded
    fun registerUser(@Field("id") id: String,
                     @Field("pw") pw: String,
                     @Field("name") name: String,
                     @Field("type") type: Int): Call<String>

    @POST("/auth/login")
    @FormUrlEncoded
    fun loginUser(@Field("id") id: String,
                     @Field("pw") pw: String): Call<String>
    //

//    @POST("/auth/login")
//    @FormUrlEncoded
//    fun loginUser(@Path("user") user: String): Single<User>


//    @POST("register")
//    @FormUrlEncoded
//    fun register(@Query("id") id:String,
//                 @Query("pw") pw: String,
//                 @Query("name") name:String,
//                 @Query("type") type: Int) :
//            Call<User>
//
//    @POST("login")
//    @FormUrlEncoded
//    fun login(@Query("id") id:String,
//              @Query("pw") pw: String) : Call<User>

}