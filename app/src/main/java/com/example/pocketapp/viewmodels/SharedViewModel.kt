package com.example.pocketapp.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.apiservicesmodule.models.UsersPostList
import com.example.apiservicesmodule.models.ModelClassGroupedByUserId
import com.example.apiservicesmodule.models.UserDetailsApiResponse
import com.example.pocketapp.repository.ApiCallBusinessLogic
import com.example.pocketapp.models.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SharedViewModel
@ViewModelInject
constructor(private val apiCallBusinessLogic: ApiCallBusinessLogic, application: Application) :
    AndroidViewModel(application) {

    var mutableLiveDataUsersPostList: MutableLiveData<DataState<List<UsersPostList>>> =
        MutableLiveData()
    var mutableLivedataObserUserdetails: MutableLiveData<DataState<UserDetailsApiResponse>> =
        MutableLiveData()
    val modelClassGroupedByUserIdList = ArrayList<ModelClassGroupedByUserId>()

    fun callHomePageApi(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetHomePageEvent -> {
                    apiCallBusinessLogic.callHomePageApi().onEach { dataState ->
                        mutableLiveDataUsersPostList.value = dataState
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    fun getUserDetails(mainStateEvent: MainStateEvent, userId: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                when (mainStateEvent) {
                    is MainStateEvent.GetUserDetailsEvent -> {
                        apiCallBusinessLogic.callUserDetailsApi(userId).onEach { dataState ->
                            mutableLivedataObserUserdetails.value = dataState
                        }.launchIn(viewModelScope)
                    }
                }
            }
        }
    }


    fun getListGroupedByUserId(homePageResponseDataList: List<UsersPostList>): List<ModelClassGroupedByUserId> {
        var observationMap = homePageResponseDataList?.groupBy { it.userId }
        modelClassGroupedByUserIdList.clear()
        observationMap.forEach { (t, u) ->
            modelClassGroupedByUserIdList.add(
                ModelClassGroupedByUserId(
                    u.get(0).userId.toString(),
                    u.size.toString(),
                    u
                )
            )
        }
        return modelClassGroupedByUserIdList
    }


    sealed class MainStateEvent {

        object GetHomePageEvent : MainStateEvent()
        object GetUserDetailsEvent : MainStateEvent()
    }
}

