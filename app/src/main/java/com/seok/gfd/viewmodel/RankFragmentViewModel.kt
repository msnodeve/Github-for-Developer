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
import com.seok.gfd.retrofit.domain.resopnse.CommitResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class RankFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application

    private val _rankList = MutableLiveData<List<CommitResponse>>()
    val rankList: LiveData<List<CommitResponse>>
        get() = _rankList

    private val _serverResult = MutableLiveData<Boolean>()
    val serverResult: LiveData<Boolean>
        get() = _serverResult

    @SuppressLint("SimpleDateFormat")
    fun getTodayRankList() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        val getTodayRankListService = RetrofitClient.commitService()
        val getTodayRankListCall = getTodayRankListService.getTRCommitList(
            "",
            dateFormat.format(Date())
        )
        getTodayRankListCall.enqueue(object : Callback<List<CommitResponse>> {
            override fun onResponse(
                call: Call<List<CommitResponse>>,
                response: Response<List<CommitResponse>>
            ) {
                val body = if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
                _rankList.postValue(body)
            }

            override fun onFailure(call: Call<List<CommitResponse>>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }

    fun updateTodayRankCommit(userId: String, dataCount: Int) {
        val updateTodayRankCommitService = RetrofitClient.commitService()
        val updateTodayRankCommitCall = updateTodayRankCommitService.enrollCommit(
            "",
            CommitRequestDto(userId, dataCount)
        )
//        updateTodayRankCommitCall.enqueue(object : Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                if (response.body().equals("CREATE")) {
//                    _serverResult.postValue(true)
//                }
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Log.e(this.javaClass.simpleName, t.message.toString())
//            }
//        })
    }
    fun getTodayRankCommitList(){
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val getTodayRankListService = RetrofitClient.commitService()
        val getTodayRankListCall = getTodayRankListService.getTRCommitList(
            "",
            dateFormat.format(Date()))
        getTodayRankListCall.enqueue(object : Callback<List<CommitResponse>>{
            override fun onResponse(
                call: Call<List<CommitResponse>>,
                response: Response<List<CommitResponse>>
            ) {
                val body = if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
                _rankList.postValue(body)
            }
            override fun onFailure(call: Call<List<CommitResponse>>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }
}