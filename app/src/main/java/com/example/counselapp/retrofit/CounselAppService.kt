package com.example.counselapp.retrofit

import com.example.counselapp.model.Post
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

    //-----------------------------------------------------------------

    // 모든 게시물 보기
    @GET("/posts")
//    @FormUrlEncoded // form 형태로 데이터 안 보내는 경우 필요 없는 듯
    fun getAllPosts(): Call<List<Post>>

    // 개별 게시물 보기
    @GET("/posts/{id}")
    fun getPost(@Path("id") id : String): Call<Post>

    // 새 게시물 생성
    @POST("/posts")
    @FormUrlEncoded
    fun writePost(@Field("commenter") commenter: String,
                    @Field("title") title: String,
                  @Field("content") content: String): Call<String>

    // 기존 게시물 수정
    @PUT("/posts/{id}")
    @FormUrlEncoded
    fun updatePost(@Path("id") id: String,
                   @Field("title") title: String,
                   @Field("content") content: String): Call<String>

    // 게시물 삭제
    @DELETE("/posts/{id}")
    fun deletePost(@Path("id") id: String): Call<String>

    //-----------------------------------------------------------------
    // 회원 정보 관련
    // 모든 상담사 목록 보기
    @GET("/users/experts")
    fun getAllExperts(): Call<List<User>>

    // 개별 회원 정보 보기
    @GET("/users/{id}")
    fun getUser(@Path("id") id : String): Call<User>




}