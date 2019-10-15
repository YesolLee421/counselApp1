package com.example.counselapp.retrofit

import com.example.counselapp.model.User
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface CounselAppService {

    @POST("register")
    @FormUrlEncoded
    fun registerUser(@Field("id") id: String,
                     @Field("pw") pw: String,
                     @Field("name") name: String,
                     @Field("type") type: Int): Observable<String>

    @POST("login")
    @FormUrlEncoded
    fun loginUser(@Field("id") id: String,
                     @Field("pw") pw: String): Observable<String>


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