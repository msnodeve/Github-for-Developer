package com.seok.gitfordeveloper.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        @JvmStatic
        lateinit var retrofit: Retrofit

        @JvmStatic
        fun getClient(url: String): Retrofit {
            val gson = GsonBuilder().setLenient().create()
            retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit
        }
    }

}