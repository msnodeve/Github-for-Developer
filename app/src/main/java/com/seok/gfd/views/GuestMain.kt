package com.seok.gfd.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.seok.gfd.R
import com.seok.gfd.adapter.ContributionsAdapter
import com.seok.gfd.retrofit.domain.resopnse.CommitsResponseDto
import com.seok.gfd.utils.Contribution
import com.seok.gfd.viewmodel.GithubContributionViewModel
import kotlinx.android.synthetic.main.activity_guest_main.*

class GuestMain : AppCompatActivity() {
    private lateinit var githubContributionViewModel: GithubContributionViewModel

    private var contributionList = ArrayList<Contribution>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_main)

        ads()
        init()
        initViewModelFun()
    }

    private fun refreshContributionsData() {
        contributionList = ArrayList()
        val userName = guest_main_search_edit.text.toString()
        githubContributionViewModel.getContributions(userName)
        val customAdapter = ContributionsAdapter(contributionList, this)
        recyclerView.adapter = customAdapter
    }

    private fun init() {
        githubContributionViewModel =
            ViewModelProviders.of(this).get(GithubContributionViewModel::class.java)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(this, 1))
        simpleSwipeRefreshLayout.setOnRefreshListener {
            simpleSwipeRefreshLayout.isRefreshing = false
            refreshContributionsData()
        }
        guest_main_search_btn.setOnClickListener {
            contributionList = ArrayList()
            guest_main_recycler_layout.visibility = View.VISIBLE
            val userName = guest_main_search_edit.text.toString()
            githubContributionViewModel.getContributions(userName)
        }
    }

    private fun initViewModelFun() {
        githubContributionViewModel.contributions.observe(this, Observer {
            if (it != null) {
                if (it.years?.size != 0) {
                    val helloName =
                        String.format("@%s on Github", guest_main_search_edit.text.toString())
                    guest_main_tv_username.text = helloName
                    getContributions(it)
                    val customAdapter = ContributionsAdapter(contributionList, this)
                    recyclerView.adapter = customAdapter
                    guest_main_recycler_layout.visibility = View.INVISIBLE
                } else {
                    guest_main_tv_username.text = "찾지 못했습니다."
                }
            } else {
                guest_main_tv_username.text = "찾지 못했습니다."
            }
            guest_main_tv_username.visibility = View.VISIBLE
        })
    }

    private fun ads() {
        MobileAds.initialize(
            this.application,
            getString(R.string.admob_app_id)
        )
        val adRequest = AdRequest.Builder().build()
        guest_login_ads_view.loadAd(adRequest)
    }


    private fun getContributions(commitsResponseDto: CommitsResponseDto) {
        for (year in commitsResponseDto.years!!) {
            var contribution = Contribution()
            contribution.year = year.year
            contribution.total = year.total
            var contributionList = ArrayList<Contribution.ContributionInfo>()
            for (index in commitsResponseDto.contributions!!.size - 1 downTo 0) {
                val commit = commitsResponseDto.contributions!![index]
                if (commit.date.contains(year.year)) {
                    val contributionInfo = Contribution.ContributionInfo(
                        commit.date,
                        commit.count,
                        commit.color,
                        commit.intensity
                    )
                    contributionList.add(contributionInfo)
                }
            }
            contribution.list = contributionList
            this.contributionList.add(contribution)
        }
    }
}
