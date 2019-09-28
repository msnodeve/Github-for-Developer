package com.seok.gitfordeveloper.retrofit.service

import com.seok.gitfordeveloper.retrofit.request.TRCommitResponseDto
import com.seok.gitfordeveloper.retrofit.request.TRCommitRequestDto
import retrofit2.Call
import retrofit2.http.*

interface TRCommitService {
    @GET("trc")
    fun getCommitList(
        @Header("Authorization") authKey: String
    ): Call<List<TRCommitResponseDto>>

    @POST("trc")
    fun insertTRCommit(
        @Header("Authorization") authKey: String,
        @Body trCommitRequestDto: TRCommitRequestDto
    ): Call<TRCommitResponseDto>

    @GET("trc/{date}")
    fun getTRCommitList(
        @Header("Authorization") authKey: String,
        @Path("date") date: String
    ): Call<List<TRCommitResponseDto>>

    @PATCH
    fun updateTRCommit(
        @Header("Authorization") authKey :String,
        @Body trCommitRequestDto: TRCommitRequestDto
    ): Call<TRCommitResponseDto>
}