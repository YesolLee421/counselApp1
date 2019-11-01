package com.example.counselapp.model
import com.google.gson.annotations.SerializedName

import java.util.*

data class Post (
    @SerializedName("_id") val _id: String,
    @SerializedName("userid") val userid: String?,
    @SerializedName("commenter") val commenter: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("date_original") val date_original: Date,
    @SerializedName("date_lastChanged") val date_lastChanged: Date,
    @SerializedName("hit") val hit: Int,
    @SerializedName("like") val like: Int,
    @SerializedName("comments") val comments: Int
    )

