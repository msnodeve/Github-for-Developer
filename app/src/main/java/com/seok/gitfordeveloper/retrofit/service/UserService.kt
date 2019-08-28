package com.seok.gitfordeveloper.retrofit.service

import com.seok.gitfordeveloper.retrofit.domain.Token
import com.seok.gitfordeveloper.retrofit.domain.User
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @GET("user")
    fun githubUserApi(@Header("Authorization") token: String): Call<User>

    @Headers("Accept: application/json")
    @POST("access_token")
    fun getGithubCode(
        @Query("client_id") clientId : String,
        @Query("client_secret") clientSecret : String,
        @Query("code") code : String) : Call<Token>
}