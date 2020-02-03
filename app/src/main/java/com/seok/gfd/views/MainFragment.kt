package com.seok.gfd.views


import android.annotation.SuppressLint
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
import com.seok.gfd.retrofit.domain.request.CommitRequestDto
import com.seok.gfd.retrofit.domain.resopnse.CommitsResponseDto
import com.seok.gfd.utils.SharedPreference
import com.seok.gfd.viewmodel.GithubCommitDataViewModel
import com.seok.gfd.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import java.time.LocalDate

class MainFragment : Fragment() {
    private lateinit var githubCommitDataViewModel: GithubCommitDataViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPreference: SharedPreference
    private lateinit var user: User

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

        // SharedPreference 에 저장된 User 정보 가져오기
        sharedPreference = SharedPreference(this.activity!!.application)
        user = sharedPreference.getValueObject(getString(R.string.user_info))

        githubCommitDataViewModel = ViewModelProviders.of(this).get(GithubCommitDataViewModel::class.java)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        // Login Activity 에서 저장한 User 정보 가져오기

        setUserInfoUI(user)


        githubCommitDataViewModel.getCommitsInfo(user.login)

//        githubCrawlerViewModel.getCommitFromGithub(user.html_url)
//        githubCrawlerViewModel.getYearCommitFromGithub(user.html_url)

    }

    private fun initViewModelFun() {
        // 금일 커밋 가져오기
        githubCommitDataViewModel.todayCommit.observe(this, Observer {
            today_commit.text = it
            sharedPreference.setValue(getString(R.string.user_today), it)
            // 금일 커밋 서버에 저장
            val commit = CommitRequestDto(user.login, it.toInt())
            userViewModel.enrollCommit(commit)
        })
        // 금년 총 커밋 가져오기
        githubCommitDataViewModel.yearCommit.observe(this, Observer {
            year_commit.text = it
            sharedPreference.setValue(getString(R.string.user_year), it)
        })
        // 커밋 최대값 가져오기
        githubCommitDataViewModel.maxCommit.observe(this, Observer {
            max_commit.text = it
            sharedPreference.setValue(getString(R.string.user_max), it)
        })
        // 총 커밋 가져오기
        githubCommitDataViewModel.commits.observe(this, Observer {
            setCommitUI(it)
        })
    }

    @SuppressLint("NewApi")
    private fun setCommitUI(it: List<CommitsResponseDto.Contribution>){
        val lastDateTime = LocalDate.now().minusDays(365).toString()
        val nowDateTime = LocalDate.now().toString()
        val lastCommitIndex = it.indexOf(it.find { it.date == lastDateTime })
        val nowCommitIndex = it.indexOf(it.find { it.date == nowDateTime })

        val t = mutableListOf<CommitsResponseDto.Contribution>()
        for (i in lastCommitIndex downTo nowCommitIndex) {
            t.add(it[i])
        }

        t
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
