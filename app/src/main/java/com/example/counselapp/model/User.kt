package com.example.counselapp.model

import java.util.*

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