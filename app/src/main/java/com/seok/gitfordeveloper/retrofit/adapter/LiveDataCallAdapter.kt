package com.seok.gitfordeveloper.retrofit.adapter

import androidx.lifecycle.LiveData
import com.seok.gitfordeveloper.retrofit.response.ApiResponse
import com.seok.gitfordeveloper.retrofit.response.UNKNOWN_CODE
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class LiveDataCallAdapter<R>(private val responseType: Type) : CallAdapter<R, LiveData<ApiResponse<R>>> {
    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            private var isSuccess = false

            override fun onInactive() {
                super.onInactive()
                dequeue()
            }

            override fun onActive() {
                super.onActive()
                if (!isSuccess) enqueue()
            }

            private fun dequeue() {
                if (call.isExecuted) call.cancel()
            }

            private fun enqueue() {
                call.enqueue(object : Callback<R> {
                    override fun onFailure(call: Call<R>, t: Throwable) {
                        postValue(ApiResponse.create(UNKNOWN_CODE, t))
                    }

                    override fun onResponse(call: Call<R>, response: Response<R>) {
                        postValue(ApiResponse.create(response))
                        isSuccess = true
                    }
                })
            }
        }
    }
    override fun responseType(): Type = responseType
}