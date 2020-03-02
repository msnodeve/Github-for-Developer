package com.seok.gfd.retrofit.service

import com.seok.gfd.retrofit.domain.resopnse.CommitsResponseDto
import com.seok.gfd.retrofit.domain.resopnse.NestedContributionsResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubContributionService {
    @GET("{userId}")
    fun getContributions(@Path("userId") userId: String): Call<CommitsResponseDto>

    @GET("{userId}")
    fun getNestedContributions(
        @Path("userId") userId: String,
        @Query("format") format: String
    ): Call<NestedContributionsResponseDto>
}