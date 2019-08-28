package com.seok.gitfordeveloper.retrofit.response

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.seok.gitfordeveloper.retrofit.RetrofitClient
import com.seok.gitfordeveloper.retrofit.domain.User
import retrofit2.Call
import retrofit2.Response

class UserRepository(application: Application) {
    private lateinit var user : User
    private val mutableLiveData = MutableLiveData<User>()
    private var application: Application = application

    public fun getMutableLiveData(token: String): MutableLiveData<User> {
        val userService = RetrofitClient.getGithubApiService()
        val call = userService.getGithubApi("token $token")
        call.enqueue(object : retrofit2.Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val body = response.body()!!
                    user = User(body.login, body.html_url, body.avatar_url)
                    mutableLiveData.value = user
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
        return mutableLiveData;
    }
}