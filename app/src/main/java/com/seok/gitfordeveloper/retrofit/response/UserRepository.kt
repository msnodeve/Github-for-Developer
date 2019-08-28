package com.seok.gitfordeveloper.retrofit.response

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.seok.gitfordeveloper.retrofit.RetrofitClient
import com.seok.gitfordeveloper.retrofit.domain.Token
import com.seok.gitfordeveloper.retrofit.domain.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.net.HttpURLConnection

class UserRepository(application: Application) {
    private lateinit var user : User
    private lateinit var token: Token
    private val userMutableData = MutableLiveData<User>()
    private val codeMutableData = MutableLiveData<Token>()
    private var application: Application = application

    fun getUserMutableData(token: String): MutableLiveData<User> {
        val userService = RetrofitClient.githubUserApiService()
        val call = userService.githubUserApi("token $token")
        call.enqueue(object : retrofit2.Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                user = if (response.isSuccessful) {
                    val body = response.body()!!
                    User(body.login, body.html_url, body.avatar_url, HttpURLConnection.HTTP_OK)
                }else{
                    User(HttpURLConnection.HTTP_UNAUTHORIZED)
                }
                userMutableData.value = user
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
        return userMutableData
    }
    fun getCodeMutableData(clientId : String, clientSecret : String, code : String) : MutableLiveData<Token>{
        val codeService = RetrofitClient.getGithubCode()
        val call = codeService.getGithubCode(clientId, clientSecret, code)
        call.enqueue(object : Callback<Token>{
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    token = Token(body.access_token, body.scope, body.token_type, HttpURLConnection.HTTP_OK)
                }else{
                    token = Token(HttpURLConnection.HTTP_NOT_FOUND)
                }
                codeMutableData.value = token
            }
            override fun onFailure(call: Call<Token>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
        return codeMutableData
    }
}