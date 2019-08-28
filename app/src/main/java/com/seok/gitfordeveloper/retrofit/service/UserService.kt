package com.seok.gitfordeveloper.retrofit.service

import com.seok.gitfordeveloper.retrofit.domain.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("user")
    fun getGithubApi(@Header("Authorization") token: String): Call<User>
}