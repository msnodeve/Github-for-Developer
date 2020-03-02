package com.seok.gfd.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seok.gfd.retrofit.RetrofitClient
import com.seok.gfd.retrofit.domain.resopnse.CommitsResponseDto
import com.seok.gfd.retrofit.domain.resopnse.NestedContributionsResponseDto
import retrofit2.Call
import retrofit2.Response

class GithubContributionViewModel : ViewModel() {
    private val _contributions = MutableLiveData<CommitsResponseDto>()
    private val _nestedContributions = MutableLiveData<NestedContributionsResponseDto>()

    val contributions: LiveData<CommitsResponseDto>
        get() = _contributions
    val nestedContributions: LiveData<NestedContributionsResponseDto>
        get() = _nestedContributions


    fun getContributions(userId: String) {
        val getContributionService = RetrofitClient.githubContributionService()
        val getContributionCall = getContributionService.getContributions(userId)
        getContributionCall.enqueue(object : retrofit2.Callback<CommitsResponseDto> {

            override fun onResponse(
                call: Call<CommitsResponseDto>,
                response: Response<CommitsResponseDto>
            ) {
                _contributions.value = response.body()
            }

            override fun onFailure(call: Call<CommitsResponseDto>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }

    fun getNestedContributions(userId: String) {
        val nestedContributionService = RetrofitClient.githubContributionService()
        val nestedContributionCall =
            nestedContributionService.getNestedContributions(userId, "nested")
        nestedContributionCall.enqueue(object : retrofit2.Callback<NestedContributionsResponseDto> {

            override fun onResponse(
                call: Call<NestedContributionsResponseDto>,
                response: Response<NestedContributionsResponseDto>
            ) {
                val t = response.body()
                t
            }

            override fun onFailure(call: Call<NestedContributionsResponseDto>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }

}