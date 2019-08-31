package com.seok.gitfordeveloper.views

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.seok.gitfordeveloper.BuildConfig
import com.seok.gitfordeveloper.R
import com.seok.gitfordeveloper.database.Commits
import com.seok.gitfordeveloper.retrofit.service.UserService
import com.seok.gitfordeveloper.utils.AuthUserInfo
import com.seok.gitfordeveloper.utils.AuthUserToken
import com.seok.gitfordeveloper.utils.GithubCrawler
import com.seok.gitfordeveloper.utils.ValidationCheck
import com.seok.gitfordeveloper.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {

    private lateinit var authUserToken: AuthUserToken
    private lateinit var authUserInfo: AuthUserInfo
    private lateinit var viewModel: MainViewModel
    private lateinit var githubCrawler: GithubCrawler
    private lateinit var validationCheck: ValidationCheck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        checkForUserInfo()
        initViewModelFun()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        authUserToken = AuthUserToken(application)
        authUserInfo = AuthUserInfo(application)
        validationCheck = ValidationCheck(application)
        githubCrawler = GithubCrawler()

        MobileAds.initialize(this, getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun initViewModelFun() {
        viewModel.commits.observe(this, Observer { body ->
            setCommitUI(body)
        })
        viewModel.existCommit.observe(this, Observer { flag ->
            if (flag) {
                viewModel.getCommits(authUserInfo.getUserEmail(getString(R.string.user_email)))
            }
        })
        viewModel.getAllCommitsComplete.observe(this, Observer { flag ->
            if (flag) {
                scroll_contribute.smoothScrollTo(contribute.width, contribute.height)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setCommitUI(body: List<Commits>) {
        runOnUiThread {
            contribute.removeAllViews()
            contribute.columnCount = 53
            contribute.rowCount = 7
            for (commit in body) {
                val layout = LinearLayout(this@MainActivity)
                val param = LinearLayout.LayoutParams(65, 65)
                param.margin = 4
                layout.layoutParams = param
                val txt = TextView(this@MainActivity)
                txt.text = commit.dataCount.toString()
                layout.gravity = Gravity.CENTER
                layout.addView(txt)
                layout.backgroundColor = Color.parseColor(commit.fill)
                contribute.addView(layout)
            }
            tv_today_commit.text =
                getString(R.string.today_commit) + " " + body[body.size - 1].dataCount
            viewModel.completeGetCommits()
        }
    }

    private fun checkForUserInfo() {
        val user = authUserInfo.getUser(
            getString(R.string.user_id),
            getString(R.string.user_email),
            getString(R.string.user_image)
        )

        if (user) {
            tv_user_id.text = authUserInfo.getUserId(getString(R.string.user_id))
            tv_github_url.text = authUserInfo.getUserEmail(getString(R.string.user_email))
            Glide.with(this).load(authUserInfo.getUserImage(getString(R.string.user_image)))
                .into(user_img_profile)
            viewModel.checkCommit()
            viewModel.getAllCommits()
        } else {
            viewModel.githubUserApi(authUserToken.getToken(BuildConfig.PREFERENCES_TOKEN_KEY))
                .observe(this, Observer { body ->
                    authUserInfo.setKeyValue(getString(R.string.user_id), body.login)
                    authUserInfo.setKeyValue(getString(R.string.user_email), body.html_url)
                    authUserInfo.setKeyValue(getString(R.string.user_image), body.avatar_url)
                    tv_user_id.text = authUserInfo.getUserId(getString(R.string.user_id))
                    tv_github_url.text = authUserInfo.getUserEmail(getString(R.string.user_email))
                    Glide.with(this).load(authUserInfo.getUserImage(getString(R.string.user_image)))
                        .into(user_img_profile)
                    viewModel.checkCommit()
                })
        }
    }
}