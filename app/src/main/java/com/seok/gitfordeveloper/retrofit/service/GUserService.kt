package com.seok.gitfordeveloper.retrofit.service

import com.seok.gitfordeveloper.retrofit.domain.GUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GUserService {
    @GET("users")
    fun getUserList() : Call<List<GUser>>

    @POST("users")
    fun signUpUser(@Body gUser: GUser) : Call<GUser>
}