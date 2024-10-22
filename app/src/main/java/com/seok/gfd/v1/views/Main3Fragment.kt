package com.seok.gfd.v1.views


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seok.gfd.R
import com.seok.gfd.retrofit.domain.User
import com.seok.gfd.retrofit.domain.request.CommitRequestDto
import com.seok.gfd.retrofit.domain.resopnse.CommitsResponseDto
import com.seok.gfd.utils.ProgressbarDialog
import com.seok.gfd.utils.SharedPreference
import com.seok.gfd.viewmodel.GithubCommitDataViewModel
import com.seok.gfd.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_main3.*
import java.time.LocalDate

class Main3Fragment : Fragment() {
    private lateinit var githubCommitDataViewModel: GithubCommitDataViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPreference: SharedPreference
    private lateinit var progressbar : ProgressbarDialog
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initViewModelFun()
    }

    private fun init() {
//        // Github 그래프 웹뷰, 줌 가능
//        val settings = wv_mv_graph.settings
//        settings.builtInZoomControls = true

        // Progress Bar
        progressbar = ProgressbarDialog(this.activity!!)
        progressbar.show()

        // SharedPreference 에 저장된 User 정보 가져오기
        sharedPreference = SharedPreference(this.activity!!.application)
        user = sharedPreference.getValueObject(getString(R.string.user_info))

        githubCommitDataViewModel =
            ViewModelProviders.of(this).get(GithubCommitDataViewModel::class.java)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        // Login Activity 에서 저장한 User 정보 가져오기

        setUserInfoUI(user)

        githubCommitDataViewModel.getCommitsInfo(user.login)

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
    private fun setCommitUI(it: List<CommitsResponseDto.Contribution>) {
        val lastDateTime = LocalDate.now().minusDays(366).toString()
        val nowDateTime = LocalDate.now().toString()
        val lastCommitIndex = it.indexOf(it.find { it.date == lastDateTime })
        val nowCommitIndex = it.indexOf(it.find { it.date == nowDateTime })
        val maxCommit = sharedPreference.getValue(getString(R.string.user_max))

        this.activity?.runOnUiThread {
            for (index in lastCommitIndex downTo nowCommitIndex) {
                val layout = LinearLayout(activity)
                val param = LinearLayout.LayoutParams(65, 65)
                val commit = it[index]
                layout.layoutParams = param
//                val txt = TextView(this.activity)
//                txt.text = commit.count.toString()
//                layout.gravity = Gravity.CENTER
//                layout.addView(txt)
                if (commit.count == maxCommit.toInt()) {
                    layout.background = activity?.getDrawable(R.drawable.rect_background)
                }
            }
        }
        progressbar.hide()
    }


    private fun setUserInfoUI(user: User) {
//        wv_mv_graph.loadUrl("https://ghchart.rshah.org/${user.login}")
        user_id.text = user.login
        user_location.text = user.location
        year_commit.text = sharedPreference.getValue(getString(R.string.user_year))
        max_commit.text = sharedPreference.getValue(getString(R.string.user_max))
        Glide.with(this).load(user.avatar_url).apply(RequestOptions.circleCropTransform())
            .into(img_mv_user_profile)
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
