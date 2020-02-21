package com.seok.gfd.retrofit.service

import com.seok.gfd.retrofit.domain.GfdUser
import com.seok.gfd.retrofit.domain.SingleResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @GET("users")
    fun getUserList(
        @Header("Authorization") authKey: String
    ): Call<List<GfdUser>>

    @POST("users")
    fun signUpUser(
        @Header("Authorization") authKey: String,
        @Body gfdUser: GfdUser
    ): Call<SingleResponseDto<GfdUser>>

    @GET("users/count")
    fun getUsersCount(
        @Header("Authorization") authKey: String
    ): Call<SingleResponseDto<Long>>

}