package com.seok.gfd.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seok.gfd.database.Commits
import com.seok.gfd.retrofit.RetrofitClient
import com.seok.gfd.retrofit.domain.resopnse.CommitsResponseDto
import org.jetbrains.anko.doAsync
import org.jsoup.Connection
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate

class GithubCommitDataViewModel: ViewModel() {
    private val _commit = MutableLiveData<Commits>()
    private val _commits = MutableLiveData<List<CommitsResponseDto.Contribution>>()
    private val _yearCommit = MutableLiveData<String>()
    private val _todayCommit = MutableLiveData<String>()
    private val _maxCommit = MutableLiveData<String>()

    val commit: LiveData<Commits>
        get() = _commit
    val commits: LiveData<List<CommitsResponseDto.Contribution>>
        get() = _commits
    val yearCommit: LiveData<String>
        get() = _yearCommit
    val todayCommit: LiveData<String>
        get() = _todayCommit
    val maxCommit: LiveData<String>
        get() = _maxCommit

    // 사용자 깃허브에서 데이터 크롤링
    fun getCommitFromGithub(uri : String){
        // Crawling 백그라운드 실행
        doAsync {
            val soup = Jsoup.connect(uri).method(Connection.Method.GET).execute().parse()
            val partOfContributionData = soup.select("div[class=js-yearly-contributions]").first()
            val lines = partOfContributionData.select("rect[class=day]")

            val contributions = ArrayList<Commits>()
            for (line in lines) {
                val attrDataDate = line.attr("data-date")
                val attrDataCount = line.attr("data-count")
                val attrFill = line.attr("fill")
                contributions.add(Commits(attrDataDate, attrDataCount.toInt(), attrFill))
            }
//            _commits.postValue(contributions)
            _commit.postValue(contributions[contributions.size-1])
            _maxCommit.postValue(contributions.maxBy { it.dataCount }?.dataCount.toString())
        }
    }

    // 사용자 1년 동안 커밋 데이 크롤링
    fun getYearCommitFromGithub(uri : String){
        // Crawling 백그라운드 실행
        doAsync {
            val soup = Jsoup.connect(uri).method(Connection.Method.GET).execute().parse()
            val contributionYearData = soup.select("h2[class=f4 text-normal mb-2]").first()
            val contribution = contributionYearData.text().split(" ")
            _yearCommit.postValue(contribution[0])
        }
    }

    // 깃허브 사용자 커밋 데이터 가져오기
    fun getCommitsInfo(userId : String){
        val getCommitsService = RetrofitClient.githubCommitService()
        val getCommitsCall = getCommitsService.getContribution(userId)
        getCommitsCall.enqueue(object : retrofit2.Callback<CommitsResponseDto> {
            @SuppressLint("NewApi")
            override fun onResponse(call: Call<CommitsResponseDto>, response: Response<CommitsResponseDto>) {
                val body = response.body()
                val max = body?.contributions?.maxBy {
                    it.count
                }
                val today = body?.contributions?.find {
                    it.date == LocalDate.now().toString()
                }
                _maxCommit.value = max?.count.toString()
                _yearCommit.value = body?.years?.get(0)?.total.toString()
                _todayCommit.value = today?.count.toString()
                _commits.value = body?.contributions
            }

            override fun onFailure(call: Call<CommitsResponseDto>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message.toString())
            }
        })
    }
}