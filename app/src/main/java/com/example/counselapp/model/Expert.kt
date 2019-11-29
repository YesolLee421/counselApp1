package com.example.counselapp.model
import com.google.gson.annotations.SerializedName

data class Expert (
    val _id: String,
    val uid: String,
    val name_formal : String,
    val about : String?,
    val belongTo: String?,
    val education : String?,
    val certificate: String?,
    val career: String?,
    val major : String?,
    val isValidated :Boolean,
    val level: Int,
    val portrait: String? // 프로필 사진
)

