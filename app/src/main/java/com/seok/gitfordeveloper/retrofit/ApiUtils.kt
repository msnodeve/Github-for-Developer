package com.seok.gitfordeveloper.retrofit

import android.util.Log
import com.seok.gitfordeveloper.retrofit.models.UserInfoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ApiUtils {

    companion object {
        val BASE_URL = "https://api.github.com/"

        @JvmStatic
        fun getUserService() : UserInfoApi{
            return RetrofitClient.getClient(BASE_URL).create(UserInfoApi::class.java)
        }
    }
}