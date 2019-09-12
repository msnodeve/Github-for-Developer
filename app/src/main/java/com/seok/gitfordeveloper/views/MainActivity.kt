package com.seok.gitfordeveloper.views

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.seok.gitfordeveloper.utils.*
import com.seok.gitfordeveloper.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    private lateinit var authUserToken: AuthUserToken
    private lateinit var authUserInfo: AuthUserInfo
    private lateinit var viewModel: MainViewModel
    private lateinit var githubCrawler: GithubCrawler
    private lateinit var validationCheck: ValidationCheck
    private lateinit var progressbarDialog: ProgressbarDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

//        init()
//        checkForUserInfo()
//        initViewModelFun()
    }

    private fun init() {
        progressbarDialog = ProgressbarDialog(this)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        authUserToken = AuthUserToken(application)
        authUserInfo = AuthUserInfo(application)
        validationCheck = ValidationCheck(application)
        githubCrawler = GithubCrawler()

        MobileAds.initialize(this, getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        adView2.loadAd(adRequest)
    }
    private fun initViewModelFun() {
        viewModel.commits.observe(this, Observer { body ->
            setCommitUI(body)
        })
        viewModel.existCommit.observe(this, Observer { flag ->
            if (flag) {
                viewModel.getCommits(authUserInfo.getUserInfo(getString(R.string.user_url)))
            }
        })
        viewModel.getAllCommitsComplete.observe(this, Observer { flag ->
            if (flag) {
                scroll_contribute.smoothScrollTo(contribute.width, contribute.height)
            }
            progressbarDialog.finish()
        })
    }
    private fun setCommitUI(body: List<Commits>) {
        val maxCommit = findMaxCommit(body)
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
                if(commit.dataCount == maxCommit.dataCount){
                    layout.background = getDrawable(R.drawable.rect_background)
                    tv_max_commit.text= getString(R.string.max_contribution) + " "+ commit.dataCount
                }
                contribute.addView(layout)
            }
            tv_today_commit.text = getString(R.string.today_contribution) + " " + body[body.size - 1].dataCount
            viewModel.completeGetCommits()
        }
    }
    private fun findMaxCommit(commits: List<Commits>):Commits {
        var commit = commits[0]
        for (c in commits) {
            if(commit.dataCount  < c.dataCount){
                commit = c
            }
        }
        return commit
    }
    private fun checkForUserInfo() {
        progressbarDialog.start()
        val user = authUserInfo.getUser(
            getString(R.string.user_id),
            getString(R.string.user_url),
            getString(R.string.user_image)
        )
        if (user) {
            tv_user_id.text = authUserInfo.getUserInfo(getString(R.string.user_id))
            tv_github_url.text = authUserInfo.getUserInfo(getString(R.string.user_url))
            Glide.with(this).load(authUserInfo.getUserInfo(getString(R.string.user_image)))
                .into(user_img_profile)
            viewModel.checkCommit()
            viewModel.getAllCommits()
        } else {
            viewModel.githubUserApi(authUserToken.getToken(BuildConfig.PREFERENCES_TOKEN_KEY))
                .observe(this, Observer { body ->
                    authUserInfo.setKeyValue(getString(R.string.user_id), body.login)
                    authUserInfo.setKeyValue(getString(R.string.user_url), body.html_url)
                    authUserInfo.setKeyValue(getString(R.string.user_image), body.avatar_url)
                    tv_user_id.text = authUserInfo.getUserInfo(getString(R.string.user_id))
                    tv_github_url.text = authUserInfo.getUserInfo(getString(R.string.user_url))
                    Glide.with(this).load(authUserInfo.getUserInfo(getString(R.string.user_image)))
                        .into(user_img_profile)
                    viewModel.checkCommit()
                })
        }
    }
}