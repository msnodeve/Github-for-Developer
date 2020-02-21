package com.seok.gfd.retrofit.service

import com.seok.gfd.retrofit.domain.MultiResponseDto
import com.seok.gfd.retrofit.domain.SingleResponseDto
import com.seok.gfd.retrofit.domain.resopnse.CommitResponse
import com.seok.gfd.retrofit.domain.request.CommitRequestDto
import com.seok.gfd.retrofit.domain.resopnse.CommitResponseDto
import retrofit2.Call
import retrofit2.http.*

interface CommitService {
    @GET("commits")
    fun getCommitsRank(
        @Header("Authorization") authKey: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<CommitResponseDto>

    @GET("commits/{dataDate}")
    fun getCommitList(
        @Header("Authorization") authKey: String,
        @Path("dataDate") dataDate: String
    ): Call<MultiResponseDto<CommitResponse>>
    @POST("commits")
    fun enrollCommit(
        @Header("Authorization") authKey: String,
        @Body commitRequestDto: CommitRequestDto
    ): Call<SingleResponseDto<Void>>

    @GET("trc/{date}")
    fun getTRCommitList(
        @Header("Authorization") authKey: String,
        @Path("date") date: String
    ): Call<List<CommitResponse>>

}