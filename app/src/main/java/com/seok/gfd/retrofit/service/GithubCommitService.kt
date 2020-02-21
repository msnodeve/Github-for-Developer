package com.seok.gfd.retrofit.service

import com.seok.gfd.retrofit.domain.resopnse.CommitsResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubCommitService {
    @GET("{userId}")
    fun getContribution(@Path("userId") userId: String):Call<CommitsResponseDto>
}