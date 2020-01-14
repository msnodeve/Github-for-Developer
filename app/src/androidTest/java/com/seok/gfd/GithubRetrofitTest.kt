package com.seok.gfd

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.seok.gfd.retrofit.service.GithubApiService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



@RunWith(AndroidJUnit4::class)
class GithubRetrofitTest {
    private val TAG = javaClass.simpleName

    private lateinit var githubGithubApiService : GithubApiService

    @Before
    fun setUp(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        githubGithubApiService = retrofit.create(GithubApiService::class.java)
    }

    @Test
    fun 깃허브_엑세스_토큰_테스트(){
        val getAccessTokenCall = githubGithubApiService.getUserInfoFromGithubApi("Bearer e339b92de18be7529bc27c28e3ee4d6ad7994fb7")
        val getAccessTokenRes = getAccessTokenCall.execute()
        assert(getAccessTokenRes.isSuccessful)
        val body = getAccessTokenRes.body()
        val errorBody = getAccessTokenRes.errorBody()

        if(body == null){
            // 새로운 토큰 값 요청해야함
            assert(true)
        }
    }
}