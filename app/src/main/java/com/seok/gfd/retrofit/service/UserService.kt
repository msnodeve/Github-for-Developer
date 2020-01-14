package com.seok.gfd.retrofit.service

import com.seok.gfd.retrofit.domain.GUser
import com.seok.gfd.retrofit.domain.SingleResponseDto
import com.seok.gfd.retrofit.domain.resopnse.UserCount
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @GET("users")
    fun getUserList(
        @Header("Authorization") authKey: String
    ): Call<List<GUser>>

    @POST("users")
    fun signUpUser(
        @Header("Authorization") authKey: String,
        @Body gUser: GUser
    ): Call<GUser>

    @GET("users/count")
    fun getUsersCount(
        @Header("Authorization") authKey: String
    ): Call<SingleResponseDto<Long>>

}