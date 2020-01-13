package com.seok.gfd.retrofit

import com.google.gson.GsonBuilder
import com.seok.gfd.BuildConfig
import com.seok.gfd.retrofit.service.UserService
import com.seok.gfd.retrofit.service.TRCommitService
import com.seok.gfd.retrofit.service.GithubUserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        lateinit var retrofit: Retrofit
        fun githubUserApiService(): GithubUserService {
            val gson = GsonBuilder().setLenient().create()
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(GithubUserService::class.java)
        }
        fun getGithubCode(): GithubUserService{
            retrofit = Retrofit.Builder()
                .baseUrl("https://github.com/login/oauth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(GithubUserService::class.java)
        }
        fun userService() : UserService{
            val gson = GsonBuilder().setLenient().create()
            retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(UserService::class.java)
        }
        fun trCommitService() : TRCommitService{
            val gson = GsonBuilder().setLenient().create()
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.GFD_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(TRCommitService::class.java)
        }
    }
}