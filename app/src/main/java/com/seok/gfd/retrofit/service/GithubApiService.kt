package com.seok.gfd.retrofit.service

import com.seok.gfd.retrofit.domain.SingleResponseDto
import com.seok.gfd.retrofit.domain.Token
import com.seok.gfd.retrofit.domain.User
import retrofit2.Call
import retrofit2.http.*

interface GithubApiService {
    @GET("user")
    fun getUserInfoFromGithubApi(@Header("Authorization") token: String): Call<User>

    @Headers("Accept: application/json")
    @POST("access_token")
    fun getAccessTokenFromGithubApi(
        @Query("client_id") clientId : String,
        @Query("client_secret") clientSecret : String,
        @Query("code") code : String) : Call<Token>
}