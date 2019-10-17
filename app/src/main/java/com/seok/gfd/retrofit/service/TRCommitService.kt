package com.seok.gfd.retrofit.service

import com.seok.gfd.retrofit.domain.resopnse.TRCommitResponseDto
import com.seok.gfd.retrofit.domain.request.TRCommitRequestDto
import retrofit2.Call
import retrofit2.http.*

interface TRCommitService {
    @GET("trc")
    fun getCommitList(
        @Header("Authorization") authKey: String
    ): Call<List<TRCommitResponseDto>>

    @POST("trc")
    fun updateTRCommit(
        @Header("Authorization") authKey: String,
        @Body trCommitRequestDto: TRCommitRequestDto
    ): Call<String>

    @GET("trc/{date}")
    fun getTRCommitList(
        @Header("Authorization") authKey: String,
        @Path("date") date: String
    ): Call<List<TRCommitResponseDto>>

}