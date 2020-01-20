package com.seok.gfd.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seok.gfd.BuildConfig
import com.seok.gfd.retrofit.RetrofitClient
import com.seok.gfd.retrofit.domain.request.CommitRequestDto
import com.seok.gfd.retrofit.domain.resopnse.TRCommitResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class RankFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application

    private val _rankList = MutableLiveData<List<TRCommitResponseDto>>()
    val rankList: LiveData<List<TRCommitResponseDto>>
        get() = _rankList

    private val _serverResult = MutableLiveData<Boolean>()
    val serverResult: LiveData<Boolean>
        get() = _serverResult

    @SuppressLint("SimpleDateFormat")
    fun getTodayRankList() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        val getTodayRankListService = RetrofitClient.trCommitService()
        val getTodayRankListCall = getTodayRankListService.getTRCommitList(
            BuildConfig.BASIC_AUTH_KEY,
            dateFormat.format(Date())
        )
        getTodayRankListCall.enqueue(object : Callback<List<TRCommitResponseDto>> {
            override fun onResponse(
                call: Call<List<TRCommitResponseDto>>,
                response: Response<List<TRCommitResponseDto>>
            ) {
                val body = if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
                _rankList.postValue(body)
            }

            override fun onFailure(call: Call<List<TRCommitResponseDto>>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }

    fun updateTodayRankCommit(userId: String, dataCount: Int) {
        val updateTodayRankCommitService = RetrofitClient.trCommitService()
        val updateTodayRankCommitCall = updateTodayRankCommitService.updateTRCommit(
            BuildConfig.BASIC_AUTH_KEY,
            CommitRequestDto(userId, dataCount)
        )
        updateTodayRankCommitCall.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body().equals("CREATE")) {
                    _serverResult.postValue(true)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }
    fun getTodayRankCommitList(){
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val getTodayRankListService = RetrofitClient.trCommitService()
        val getTodayRankListCall = getTodayRankListService.getTRCommitList(
            BuildConfig.BASIC_AUTH_KEY,
            dateFormat.format(Date()))
        getTodayRankListCall.enqueue(object : Callback<List<TRCommitResponseDto>>{
            override fun onResponse(
                call: Call<List<TRCommitResponseDto>>,
                response: Response<List<TRCommitResponseDto>>
            ) {
                val body = if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
                _rankList.postValue(body)
            }
            override fun onFailure(call: Call<List<TRCommitResponseDto>>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }
}