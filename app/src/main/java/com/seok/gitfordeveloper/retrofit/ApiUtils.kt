package com.seok.gitfordeveloper.retrofit

import com.seok.gitfordeveloper.retrofit.service.GithubUserService

class ApiUtils {
    companion object {
        val BASE_URL = "https://api.github.com/"

        fun getUserService() : GithubUserService {
            return RetrofitClient.getClient(BASE_URL).create(GithubUserService::class.java)
        }
    }
}