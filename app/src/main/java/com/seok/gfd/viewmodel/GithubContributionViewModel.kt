package com.seok.gfd.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seok.gfd.retrofit.RetrofitClient
import com.seok.gfd.retrofit.domain.resopnse.CommitsResponseDto
import retrofit2.Call
import retrofit2.Response

class GithubContributionViewModel : ViewModel() {
    private val _commits = MutableLiveData<CommitsResponseDto>()

    val commits: LiveData<CommitsResponseDto>
        get() = _commits


    fun getContributionData(userId: String) {
        val getContributionService = RetrofitClient.githubCommitService()
        val getContributionCall = getContributionService.getContribution(userId)
        getContributionCall.enqueue(object : retrofit2.Callback<CommitsResponseDto> {

            override fun onResponse(call: Call<CommitsResponseDto>, response: Response<CommitsResponseDto>) {
                _commits.value = response.body()
            }

            override fun onFailure(call: Call<CommitsResponseDto>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }

        })
    }
}