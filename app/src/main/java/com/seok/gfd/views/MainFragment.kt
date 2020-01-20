package com.seok.gfd.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seok.gfd.R
import com.seok.gfd.retrofit.domain.User
import com.seok.gfd.utils.SharedPreference
import com.seok.gfd.viewmodel.GithubCrawlerViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    private lateinit var githubCrawlerViewModel: GithubCrawlerViewModel
    private lateinit var sharedPreference: SharedPreference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initViewModelFun()
    }

    private fun init() {
        // Github 그래프 웹뷰, 줌 가능
        val settings = wv_mv_graph.settings
        settings.builtInZoomControls = true

        sharedPreference = SharedPreference(this.activity!!.application)
        githubCrawlerViewModel = ViewModelProviders.of(this).get(GithubCrawlerViewModel::class.java)
        // Login Activity 에서 저장한 User 정보 가져오기
        val user = sharedPreference.getValueObject(getString(R.string.user_info))
        setUserInfoUI(user)

        githubCrawlerViewModel.getCommitFromGithub(user.html_url)
        githubCrawlerViewModel.getYearCommitFromGithub(user.html_url)
    }

    private fun initViewModelFun() {
        githubCrawlerViewModel.commit.observe(this, Observer {
            today_commit.text = it.dataCount.toString()
        })
        githubCrawlerViewModel.commits.observe(this, Observer {
            // Room db에 저장하는 코드 작성하기
        })
        githubCrawlerViewModel.maxCommits.observe(this, Observer {
            max_commit.text = it
            sharedPreference.setValue(getString(R.string.user_max), it)
        })
        githubCrawlerViewModel.yearCommit.observe(this, Observer {
            year_commit.text = it
            sharedPreference.setValue(getString(R.string.user_year), it)
        })
    }

    private fun setUserInfoUI(user: User) {
        wv_mv_graph.loadUrl("https://ghchart.rshah.org/${user.login}")
        user_id.text = user.login
        user_location.text = user.location
        year_commit.text = sharedPreference.getValue(getString(R.string.user_year))
        max_commit.text = sharedPreference.getValue(getString(R.string.user_max))
        Glide.with(this).load(user.avatar_url).apply(RequestOptions.circleCropTransform()).into(img_mv_user_profile)
    }

    /** 오리지날 잔디 그래프 출력
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
            val todayCommit = it[it.size - 1].dataCount
            today_commit.text = todayCommit.toString()
        }
    }
    */
}
