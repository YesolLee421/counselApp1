package com.example.counselapp.model

data class User (
    val idx : Int,
    val id : String,
    val pw : String,
    val type : Int,
    val name : String,
    val description : String?,
    val belongTo :String?


)