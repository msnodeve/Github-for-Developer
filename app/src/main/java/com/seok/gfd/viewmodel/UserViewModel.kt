package com.seok.gfd.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seok.gfd.BuildConfig
import com.seok.gfd.retrofit.RetrofitClient
import com.seok.gfd.retrofit.domain.SingleResponseDto
import retrofit2.Call
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val _usersCount = MutableLiveData<Long>()

    val userCount: LiveData<Long>
        get() = _usersCount


    // User 인원 수 가져오기
    fun getUsersCount(){
        val userService = RetrofitClient.userService()
        val userCall = userService.getUsersCount(BuildConfig.BASIC_AUTH_KEY)
        userCall.enqueue(object : retrofit2.Callback<SingleResponseDto<Long>>{
            override fun onResponse(call: Call<SingleResponseDto<Long>>, response: Response<SingleResponseDto<Long>>) {
                _usersCount.value  = response.body()?.data
            }
            override fun onFailure(call: Call<SingleResponseDto<Long>>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }

}