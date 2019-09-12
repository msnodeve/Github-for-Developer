package com.seok.gitfordeveloper.views


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.seok.gitfordeveloper.R
import com.seok.gitfordeveloper.database.Commits
import com.seok.gitfordeveloper.utils.AuthUserInfo
import com.seok.gitfordeveloper.utils.SharedPreferencesForUser
import com.seok.gitfordeveloper.viewmodel.MainFragmentViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.margin

class MainFragment : Fragment() {

    private lateinit var sharedPreferencesForUser: SharedPreferencesForUser

    private lateinit var authUserInfo: AuthUserInfo
    private lateinit var viewModel: MainFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initViewModelFun()
        checkForUserInfo()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
        sharedPreferencesForUser = SharedPreferencesForUser(this.activity?.application!!)

        authUserInfo = AuthUserInfo(this.activity?.application!!)

        MobileAds.initialize(
            this.activity!!.application,
            getString(R.string.banner_ad_unit_id_for_test)
        )
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun initViewModelFun() {
        viewModel.user.observe(this, Observer {
            setUserInfoUI(it.login, it.html_url, it.avatar_url)
            viewModel.getCommitsFromGithub()
        })
        viewModel.commits.observe(this, Observer {
            setCommitUI(it)
        })
        viewModel.commit.observe(this, Observer {
            max_commit.text = it.dataCount.toString()
        })
        viewModel.completeGetAllCommits.observe(this, Observer {
            if(it) {
                scroll_contribute.smoothScrollTo(contribute.width, contribute.height)
            }
        })
    }

    private fun checkForUserInfo() {
        viewModel.setUserInfo()
    }

    private fun setCommitUI(it: List<Commits>) {
        val maxCommit = it.maxBy { it.dataCount }
        this.activity?.runOnUiThread {
            contribute.removeAllViews()
            contribute.columnCount = 53
            contribute.rowCount = 7
            for (commit in it) {
                val layout = LinearLayout(this.activity)
                val param = LinearLayout.LayoutParams(65, 65)
                param.margin = 4
                layout.layoutParams = param
                val txt = TextView(this.activity)
                txt.text = commit.dataCount.toString()
                layout.gravity = Gravity.CENTER
                layout.addView(txt)
                layout.backgroundColor = Color.parseColor(commit.fill)
                if (commit.dataCount == maxCommit!!.dataCount) {
                    layout.background = this.activity?.getDrawable(R.drawable.rect_background)
                    max_commit.text = commit.dataCount.toString()
                }
                contribute.addView(layout)
            }
            today_commit.text = it[it.size - 1].dataCount.toString()
            viewModel.completeGetAllCommits()
        }
    }

    private fun setUserInfoUI(userId: String, userUrl: String, userImage: String) {
        user_id.text = userId
        user_url.text = userUrl
        Glide.with(this).load(userImage).into(user_profile)
    }
}
