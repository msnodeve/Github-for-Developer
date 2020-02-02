package com.seok.gfd.retrofit

import com.google.gson.GsonBuilder
import com.seok.gfd.BuildConfig
import com.seok.gfd.retrofit.service.UserService
import com.seok.gfd.retrofit.service.CommitService
import com.seok.gfd.retrofit.service.GithubApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        lateinit var retrofit: Retrofit
        fun githubApiService(): GithubApiService {
            val gson = GsonBuilder().setLenient().create()
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(GithubApiService::class.java)
        }
        fun githubAuthService(): GithubApiService{
            retrofit = Retrofit.Builder()
                .baseUrl("https://github.com/login/oauth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(GithubApiService::class.java)
        }
        fun userService() : UserService{
            val gson = GsonBuilder().setLenient().create()
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.GFD_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(UserService::class.java)
        }
        fun commitService() : CommitService{
            val gson = GsonBuilder().setLenient().create()
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.GFD_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(CommitService::class.java)
        }
    }
}