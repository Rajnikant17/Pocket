package com.example.apiservicesmodule.models

data class ModelClassGroupedByUserId
    (var user_id :String ,
     var noOfPosts :String ,
    val homepageApiResposeList:List<UsersPostList>
)