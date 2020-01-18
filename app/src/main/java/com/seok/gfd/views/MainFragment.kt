package com.seok.gfd.views


import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.seok.gfd.R
import com.seok.gfd.database.Commits
import com.seok.gfd.utils.AuthUserInfo
import com.seok.gfd.utils.ProgressbarDialog
import com.seok.gfd.utils.SharedPreferencesForUser
import com.seok.gfd.viewmodel.MainFragmentViewModel
import com.seok.gfd.viewmodel.RankFragmentViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.margin

class MainFragment : Fragment() {

    private lateinit var sharedPreferencesForUser: SharedPreferencesForUser
    private lateinit var authUserInfo: AuthUserInfo
    private lateinit var mainViewModel: MainFragmentViewModel
    private lateinit var rankViewModel: RankFragmentViewModel
    private lateinit var progressbarDialog: ProgressbarDialog

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

        val settings = wv_mv_graph.settings
        settings.builtInZoomControls = true

        wv_mv_graph.loadUrl("https://ghchart.rshah.org/msnodeve")
    }

    private fun init() {
        progressbarDialog = ProgressbarDialog(context!!)
        mainViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
        rankViewModel = ViewModelProviders.of(this).get(RankFragmentViewModel::class.java)
        sharedPreferencesForUser = SharedPreferencesForUser(this.activity?.application!!)

        authUserInfo = AuthUserInfo(this.activity?.application!!)
    }

    private fun initViewModelFun() {
        mainViewModel.user.observe(this, Observer {
            setUserInfoUI(it.login, it.html_url, it.avatar_url)
            mainViewModel.getCommitsFromGithub()
        })
        mainViewModel.commits.observe(this, Observer {
            setCommitUI(it)
        })
        mainViewModel.commit.observe(this, Observer {
            max_commit.text = it.dataCount.toString()
        })
        mainViewModel.completeGetAllCommits.observe(this, Observer {
            if (it) {
                scroll_contribute.smoothScrollTo(contribute.width, contribute.height)
            }
        })
    }

    private fun checkForUserInfo() {
        progressbarDialog.show()
        mainViewModel.setUserInfo()
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
//                txt.text = commit.dataCount.toString()
                layout.gravity = Gravity.CENTER
                layout.addView(txt)
                layout.backgroundColor = Color.parseColor(commit.fill)
                if (commit.dataCount == maxCommit!!.dataCount) {
                    layout.background = this.activity?.getDrawable(R.drawable.rect_background)
                    max_commit.text = commit.dataCount.toString()
                }
                contribute.addView(layout)
            }
            val todayCommit = it[it.size - 1].dataCount
            today_commit.text = todayCommit.toString()
            mainViewModel.completeGetAllCommits()
            rankViewModel.updateTodayRankCommit(
                sharedPreferencesForUser.getValue(getString(R.string.user_id)),
                todayCommit
            )
        }
        progressbarDialog.hide()
    }

    private fun setUserInfoUI(userId: String, userUrl: String, userImage: String) {
        user_id.text = userId
        Glide.with(this).load(userImage).into(img_mv_user_profile)
    }
}