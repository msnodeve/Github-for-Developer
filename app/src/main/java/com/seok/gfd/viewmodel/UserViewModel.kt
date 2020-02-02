package com.seok.gfd.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seok.gfd.BuildConfig
import com.seok.gfd.retrofit.RetrofitClient
import com.seok.gfd.retrofit.domain.*
import com.seok.gfd.retrofit.domain.resopnse.CommitResponse
import retrofit2.Call
import retrofit2.Response
import java.net.HttpURLConnection
import java.time.LocalDate

class UserViewModel : ViewModel() {
    private val _usersCount = MutableLiveData<Long>()
    private val _accessToken = MutableLiveData<String>()
    private val _code = MutableLiveData<Int>()
    private val _userInfo = MutableLiveData<User>()
    private val _commitList = MutableLiveData<List<CommitResponse>>()

    val userCount: LiveData<Long>
        get() = _usersCount
    val accessToken: LiveData<String>
        get() = _accessToken
    val code: LiveData<Int>
        get() = _code
    val userInfo: LiveData<User>
        get() = _userInfo
    val commitList: LiveData<List<CommitResponse>>
        get() = _commitList

    // User 인원 수 가져오기
    fun getUsersCount() {
        val userService = RetrofitClient.userService()
        val userCall = userService.getUsersCount(BuildConfig.BASIC_AUTH_KEY)
        userCall.enqueue(object : retrofit2.Callback<SingleResponseDto<Long>> {
            override fun onResponse(
                call: Call<SingleResponseDto<Long>>,
                response: Response<SingleResponseDto<Long>>
            ) {
                _usersCount.value = response.body()?.data
            }

            override fun onFailure(call: Call<SingleResponseDto<Long>>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }

    // Access Token 가져오기
    fun getAccessTokenFromGithubApi(code: String) {
        val githubAuthService = RetrofitClient.githubAuthService()
        val githubAuthCall = githubAuthService.getAccessTokenFromGithubApi(
            BuildConfig.GITHUB_CLIENT_ID,
            BuildConfig.GITHUB_CLIENT_SECRET,
            code
        )
        githubAuthCall.enqueue(object : retrofit2.Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                _accessToken.value = response.body()?.access_token
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }

    // Github 로그인 및 User 정보 가져오기
    fun getUserInfoAndSignInGithub(accessToken: String) {
        val githubApiService = RetrofitClient.githubApiService()
        val githubApiCall = githubApiService.getUserInfoFromGithubApi("token $accessToken")
        githubApiCall.enqueue(object : retrofit2.Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                _code.value = response.code()
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    _userInfo.value = response.body()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }

    fun signInUserInfo(user: User){
        val gfdSignInService = RetrofitClient.userService()
        val requestUserDto = GfdUser(user.login, user.html_url, user.avatar_url)
        val gfdSignInCall = gfdSignInService.signUpUser(BuildConfig.BASIC_AUTH_KEY, requestUserDto)
        gfdSignInCall.enqueue(object : retrofit2.Callback<SingleResponseDto<GfdUser>>{
            override fun onResponse(
                call: Call<SingleResponseDto<GfdUser>>,
                response: Response<SingleResponseDto<GfdUser>>
            ) {
                response
            }
            override fun onFailure(call: Call<SingleResponseDto<GfdUser>>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }

        })
    }

    // 금일 커밋 랭킹 가져오기
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCommitsRank() {
        val dataDate = LocalDate.now().toString()
        val getCommitsService = RetrofitClient.commitService()
        val getCommitCall = getCommitsService.getCommitList(BuildConfig.BASIC_AUTH_KEY, dataDate)
        getCommitCall.enqueue(object : retrofit2.Callback<MultiResponseDto<CommitResponse>> {
            override fun onResponse(call: Call<MultiResponseDto<CommitResponse>>, response: Response<MultiResponseDto<CommitResponse>>) {
                _commitList.value = response.body()?.list
            }

            override fun onFailure(call: Call<MultiResponseDto<CommitResponse>>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }

        })
    }
}