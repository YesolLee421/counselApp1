package com.example.counselapp.model

import java.util.*

// 프로필 사진 경로 넣어야 함
data class User (
    val _id: String,
    val id : String,
    val pw : String,
    val type : Int,
    val name : String,
    val createdAt: Date,
    val description : String?,
    val belongTo :String?
)