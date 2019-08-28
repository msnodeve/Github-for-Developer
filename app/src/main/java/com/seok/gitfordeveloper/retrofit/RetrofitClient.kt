package com.seok.gitfordeveloper.retrofit

import com.google.gson.GsonBuilder
import com.seok.gitfordeveloper.retrofit.adapter.LiveDataCallAdapterFactory
import com.seok.gitfordeveloper.retrofit.service.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        lateinit var retrofit: Retrofit
        fun getGithubApiService(): UserService {
            val gson = GsonBuilder().setLenient().create()
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(UserService::class.java)
        }
    }
}