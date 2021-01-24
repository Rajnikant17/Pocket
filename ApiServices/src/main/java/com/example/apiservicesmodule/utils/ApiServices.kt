package com.example.apiservicesmodule.utils

import com.example.apiservicesmodule.models.UsersPostList
import com.example.apiservicesmodule.models.UserDetailsApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("/posts")
    suspend fun getHomePageData() :List<UsersPostList>

    @GET("/users/{userId}")
    suspend fun getUserDetails(@Path("userId") userId:Int) :UserDetailsApiResponse

}