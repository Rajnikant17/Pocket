package com.example.apiservicesmodule.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDetailsApiResponse (
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("email")
    @Expose
    val email: String
)
