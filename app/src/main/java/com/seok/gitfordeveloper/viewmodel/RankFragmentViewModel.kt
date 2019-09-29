package com.seok.gitfordeveloper.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seok.gitfordeveloper.BuildConfig
import com.seok.gitfordeveloper.retrofit.RetrofitClient
import com.seok.gitfordeveloper.retrofit.request.TRCommitResponseDto
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


    @SuppressLint("SimpleDateFormat")
    fun getTodayRankList(){
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        val getTodayRankListService = RetrofitClient.trCommitService()
        val getTodayRankListCall = getTodayRankListService.getTRCommitList(
            BuildConfig.BASIC_AUTH_KEY,
            dateFormat.format(Date()))
        getTodayRankListCall.enqueue(object : Callback<List<TRCommitResponseDto>>{
            override fun onResponse(call: Call<List<TRCommitResponseDto>>, response: Response<List<TRCommitResponseDto>>) {
                val body = if(response.isSuccessful){
                    response.body()
                }else{
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