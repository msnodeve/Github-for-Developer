package com.seok.gfd.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seok.gfd.BuildConfig
import com.seok.gfd.retrofit.RetrofitClient
import com.seok.gfd.retrofit.domain.SingleResponseDto
import com.seok.gfd.retrofit.domain.Token
import com.seok.gfd.retrofit.domain.User
import com.seok.gfd.retrofit.domain.resopnse.CommitResponseDto
import com.seok.gfd.retrofit.domain.resopnse.CommitResponse
import retrofit2.Call
import retrofit2.Response
import java.net.HttpURLConnection

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

    // 금일 커밋 랭킹 가져오기
    fun getCommitsRank(page : Int) {
        val getCommitsService = RetrofitClient.commitService()
        val getCommitCall = getCommitsService.getCommitsRank(BuildConfig.BASIC_AUTH_KEY, page, 5)
        getCommitCall.enqueue(object : retrofit2.Callback<CommitResponseDto> {
            override fun onResponse(call: Call<CommitResponseDto>, response: Response<CommitResponseDto>) {
                _commitList.value = response.body()?.data?.content
            }

            override fun onFailure(call: Call<CommitResponseDto>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }

        })
    }
}