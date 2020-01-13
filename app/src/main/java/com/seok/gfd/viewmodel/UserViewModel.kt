package com.seok.gfd.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seok.gfd.BuildConfig
import com.seok.gfd.retrofit.RetrofitClient
import com.seok.gfd.retrofit.domain.User
import com.seok.gfd.retrofit.domain.resopnse.UserCount
import retrofit2.Call
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val _usersCount = MutableLiveData<Int>()

    val userCount: LiveData<Int>
        get() = _usersCount


    fun getUsersCount(){
        val userService = RetrofitClient.userService()
        val userCall = userService.getUsersCount(BuildConfig.BASIC_AUTH_KEY)
        userCall.enqueue(object : retrofit2.Callback<UserCount>{
            override fun onResponse(call: Call<UserCount>, response: Response<UserCount>) {
                response
            }
            override fun onFailure(call: Call<UserCount>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }

}