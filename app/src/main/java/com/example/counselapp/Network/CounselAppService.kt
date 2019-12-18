package com.example.counselapp.Network

import com.example.counselapp.model.Expert
import com.example.counselapp.model.Post
import com.example.counselapp.model.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface CounselAppService {

    @POST("/auth/register")
    @FormUrlEncoded
    fun registerUser(@Field("email") id: String,
                     @Field("pw") pw: String,
                     @Field("name") name: String,
                     @Field("type") type: Int): Call<String>

    @POST("/auth/login")
    @FormUrlEncoded
    fun loginUser(@Field("email") id: String,
                     @Field("pw") pw: String): Call<String>

    @GET("/auth/logout")
    fun logoutUser(): Call<String>

    //-----------------------------------------------------------------

    // 모든 게시물 보기
    @GET("/posts")
    fun getAllPosts(): Call<List<Post>>

    // 개별 게시물 보기
    @GET("/posts/{id}")
    fun getPost(@Path("id") id : String): Call<Post>

    // 새 게시물 생성: 사진 경로(multipart)
//    @POST("/posts")
//    @FormUrlEncoded
//    fun writePost(@Field("commenter") commenter: String,
//                    @Field("title") title: String,
//                  @Field("content") content: String): Call<String>

    @POST("/posts")
    @Multipart
    fun writePost(@Part("title") title: String,
                  @Part("content") content: String,
                  @Part("picture") picture: File?): Call<String>

    // 기존 게시물 수정
//    @PUT("/posts/{id}")
//    @FormUrlEncoded
//    fun updatePost(@Path("id") id: String,
//                   @Field("title") title: String,
//                   @Field("content") content: String): Call<String>

    @PUT("/posts/{id}")
    @Multipart
    fun updatePost(@Path("id") id: String,
                   @Part("title") title: String,
                   @Part("content") content: String,
                   @Part("picture") picture: File?): Call<String>


    // 게시물 삭제
    @DELETE("/posts/{id}")
    fun deletePost(@Path("id") id: String): Call<String>

    //-----------------------------------------------------------------
    // 회원 정보 관련
    // 모든 상담사 목록 보기
    @GET("/users/allexperts")
    fun getAllExperts(): Call<List<Expert>>

    // 개별 회원 정보 보기
    @GET("/users")
    fun getUser(): Call<User>

    // 상담사 정보 보기
    @GET("/users/expert/{id}")
    fun getExpert(@Path("id") expertid: String): Call<Expert>

    // 상담사 정보 입력(수정): 프로필 사진 업로드 넣기(multipart)
//    @PUT("/users/expert/update")
//    @FormUrlEncoded
//    fun updateExpert(@Field("name_formal") name_formal: String,
//                     @Field("about") about: String?,
//                     @Field("belongTo") belongTo: String?,
//                     @Field("education") education: String?,
//                       @Field("career") career: String?,
//                       @Field("certificate") certificate: String?,
//                     @Field("major") major: String?,
//                     @Field("portrait") portrait: String?): Call<String>

    // 상담사 정보 입력(수정): 프로필 사진 업로드 넣기(multipart)
    @PUT("/users/expert/update")
    @Multipart
    fun updateExpert(@Part("name_formal") name_formal: String,
                     @Part("about") about: String?,
                     @Part("belongTo") belongTo: String?,
                     @Part("education") education: String?,
                     @Part("career") career: String?,
                     @Part("certificate") certificate: String?,
                     @Part("major") major: String?,
                     @Part portrait: MultipartBody.Part?): Call<String>
}