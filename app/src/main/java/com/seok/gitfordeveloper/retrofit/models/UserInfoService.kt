package com.seok.gitfordeveloper.retrofit.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

data class UserInfoService(
    val login: String,
    val avatar_url: String,
    val html_url: String
)

interface UserInfoApi {
    @GET("user")
    fun getUserInfo(@Header("Authorization") token: String): Call<UserInfoService>
}