package com.seok.gfd.retrofit.service

import com.seok.gfd.retrofit.domain.resopnse.CommitResponseDto
import com.seok.gfd.retrofit.domain.request.CommitRequestDto
import retrofit2.Call
import retrofit2.http.*

interface TRCommitService {
    @GET("trc")
    fun getCommitList(
        @Header("Authorization") authKey: String
    ): Call<List<CommitResponseDto>>

    @POST("trc")
    fun updateTRCommit(
        @Header("Authorization") authKey: String,
        @Body commitRequestDto: CommitRequestDto
    ): Call<String>

    @GET("trc/{date}")
    fun getTRCommitList(
        @Header("Authorization") authKey: String,
        @Path("date") date: String
    ): Call<List<CommitResponseDto>>

}