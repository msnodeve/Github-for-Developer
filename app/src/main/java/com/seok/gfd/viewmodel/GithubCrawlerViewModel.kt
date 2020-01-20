package com.seok.gfd.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seok.gfd.database.Commits
import org.jetbrains.anko.doAsync
import org.jsoup.Connection
import org.jsoup.Jsoup

class GithubCrawlerViewModel: ViewModel() {
    private val _commit = MutableLiveData<Commits>()
    private val _commits = MutableLiveData<List<Commits>>()
    private val _yearCommit = MutableLiveData<String>()
    private val _maxCommits = MutableLiveData<String>()

    val commit: LiveData<Commits>
        get() = _commit
    val commits: LiveData<List<Commits>>
        get() = _commits
    val yearCommit: LiveData<String>
        get() = _yearCommit
    val maxCommits: LiveData<String>
        get() = _maxCommits

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
            _commits.postValue(contributions)
            _commit.postValue(contributions[contributions.size-1])
            _maxCommits.postValue(contributions.maxBy { it.dataCount }?.dataCount.toString())
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
}