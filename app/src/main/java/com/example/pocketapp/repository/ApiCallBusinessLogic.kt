package com.example.pocketapp.repository

import com.example.apiservicesmodule.utils.ApiServices
import com.example.apiservicesmodule.models.UsersPostList
import com.example.apiservicesmodule.models.UserDetailsApiResponse
import com.example.pocketapp.models.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiCallBusinessLogic
@Inject
constructor(val apiServices: ApiServices) {
    // Api called for getting users posts
    suspend fun callHomePageApi(): Flow<DataState<List<UsersPostList>>> = flow {
        emit(DataState.Loading)
        try {
            val getUserPostList = apiServices.getHomePageData()
            emit(DataState.Success(getUserPostList))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    // Api called for getting user details
    suspend fun callUserDetailsApi(userId: Int): Flow<DataState<UserDetailsApiResponse>> = flow {
        emit(DataState.Loading)
        try {
            val getUserDetailsApiResponse = apiServices.getUserDetails(userId)
            emit(DataState.Success(getUserDetailsApiResponse))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}