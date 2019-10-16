package com.seok.gitfordeveloper.retrofit.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.seok.gitfordeveloper.BuildConfig
import com.seok.gitfordeveloper.retrofit.RetrofitClient
import com.seok.gitfordeveloper.retrofit.domain.GUser
import com.seok.gitfordeveloper.retrofit.domain.Token
import com.seok.gitfordeveloper.retrofit.domain.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class UserRepository(application: Application) {
    private lateinit var user: User
    private lateinit var token: Token
    private val userMutableData = MutableLiveData<User>()
    private val codeMutableData = MutableLiveData<Token>()
    private var application: Application = application

    fun getUserMutableData(token: String): MutableLiveData<User> {
        val userService = RetrofitClient.githubUserApiService()
        val call = userService.githubUserApi("token $token")
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val body = response.body()!!
                    user =
                        User(body.login, body.html_url, body.avatar_url, HttpURLConnection.HTTP_OK)
                    val signUpService = RetrofitClient.gUserService()
                    val signUpCall = signUpService.signUpUser(
                        BuildConfig.BASIC_AUTH_KEY
                        , GUser(body.login, body.html_url, body.avatar_url)
                    )
                    signUpCall.enqueue(object :Callback<GUser>{
                        override fun onResponse(call: Call<GUser>, response: Response<GUser>) {
                            Log.d(this.javaClass.simpleName, "Success signUp")
                        }
                        override fun onFailure(call: Call<GUser>, t: Throwable) {
                            Log.e(this.javaClass.simpleName, t.message.toString())
                        }
                    })
                } else {
                    user = User(HttpURLConnection.HTTP_UNAUTHORIZED)
                }
                userMutableData.value = user
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
        return userMutableData
    }

    fun getCodeMutableData(
        clientId: String,
        clientSecret: String,
        code: String
    ): MutableLiveData<Token> {
        val codeService = RetrofitClient.getGithubCode()
        val call = codeService.getGithubCode(clientId, clientSecret, code)
        call.enqueue(object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                token = if (response.isSuccessful) {
                    val body = response.body()!!
                    Token(
                        body.access_token,
                        body.scope,
                        body.token_type,
                        HttpURLConnection.HTTP_OK
                    )
                } else {
                    Token(HttpURLConnection.HTTP_NOT_FOUND)
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