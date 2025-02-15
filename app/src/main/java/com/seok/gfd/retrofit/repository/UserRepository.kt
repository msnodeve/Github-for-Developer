package com.seok.gfd.retrofit.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.seok.gfd.BuildConfig
import com.seok.gfd.retrofit.RetrofitClient
import com.seok.gfd.retrofit.domain.GfdUser
import com.seok.gfd.retrofit.domain.Token
import com.seok.gfd.retrofit.domain.User
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
        val userService = RetrofitClient.githubApiService()
        val call = userService.getUserInfoFromGithubApi("token $token")
//        call.enqueue(object : Callback<User> {
//            override fun onResponse(call: Call<User>, response: Response<User>) {
//                if (response.isSuccessful) {
//                    val body = response.body()!!
//                    user =
//                        User(body.login, body.html_url, body.avatar_url, HttpURLConnection.HTTP_OK)
//                    val signUpService = RetrofitClient.userService()
//                    val signUpCall = signUpService.signUpUser(
//                        BuildConfig.BASIC_AUTH_KEY
//                        , GfdUser(body.login, body.html_url, body.avatar_url)
//                    )
//                    signUpCall.enqueue(object :Callback<GfdUser>{
//                        override fun onResponse(call: Call<GfdUser>, response: Response<GfdUser>) {
//                            Log.d(this.javaClass.simpleName, "Success signUp")
//                        }
//                        override fun onFailure(call: Call<GfdUser>, t: Throwable) {
//                            Log.e(this.javaClass.simpleName, t.message.toString())
//                        }
//                    })
//                } else {
//                    user = User(HttpURLConnection.HTTP_UNAUTHORIZED)
//                }
//                userMutableData.value = user
//            }
//
//            override fun onFailure(call: Call<User>, t: Throwable) {
//                Log.e(this.javaClass.simpleName, t.message.toString())
//            }
//        })
        return userMutableData
    }

//    fun getCodeMutableData(
//        clientId: String,
//        clientSecret: String,
//        code: String
//    ): MutableLiveData<Token> {
//        val codeService = RetrofitClient.githubAuthService()
//        val call = codeService.getAccessTokenFromGithubApi(clientId, clientSecret, code)
//        call.enqueue(object : Callback<Token> {
//            override fun onResponse(call: Call<Token>, response: Response<Token>) {
//                token = if (response.isSuccessful) {
//                    val body = response.body()!!
//                    Token(
//                        body.access_token,
//                        body.scope,
//                        body.token_type,
//                        HttpURLConnection.HTTP_OK
//                    )
//                } else {
//                    Token(HttpURLConnection.HTTP_NOT_FOUND)
//                }
//                codeMutableData.value = token
//            }
//
//            override fun onFailure(call: Call<Token>, t: Throwable) {
//                Log.e(this.javaClass.simpleName, t.message.toString())
//            }
//        })
//        return codeMutableData
//    }
}