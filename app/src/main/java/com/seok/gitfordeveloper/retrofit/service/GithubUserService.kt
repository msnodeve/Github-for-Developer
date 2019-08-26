package com.seok.gitfordeveloper.retrofit.service

import com.seok.gitfordeveloper.retrofit.domain.GithubUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface GithubUserService {

    @Headers("Content-Type: application/json")
    @GET("user")
    fun getGithubApi(@Header("Authorization") token: String): Call<GithubUser>
}