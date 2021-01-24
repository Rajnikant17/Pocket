package com.example.apiservicesmodule.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UsersPostList (
    @SerializedName("userId")
    @Expose
    var userId: Int,
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("body")
    @Expose
    val body: String

)