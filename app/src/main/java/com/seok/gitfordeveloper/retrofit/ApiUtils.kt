package com.seok.gitfordeveloper.retrofit


import com.seok.gitfordeveloper.retrofit.models.UserInfoApi

class ApiUtils {

    companion object {
        val BASE_URL = "https://api.github.com/"

        @JvmStatic
        fun getUserService() : UserInfoApi{
            return RetrofitClient.getClient(BASE_URL).create(UserInfoApi::class.java)
        }
    }
}