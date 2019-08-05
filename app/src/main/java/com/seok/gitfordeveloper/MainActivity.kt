package com.seok.gitfordeveloper

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.seok.gitfordeveloper.retrofit.ApiUtils
import com.seok.gitfordeveloper.retrofit.models.GithubApiUserInfo
import com.seok.gitfordeveloper.room.database.CommitDB
import com.seok.gitfordeveloper.room.model.User
import com.seok.gitfordeveloper.room.database.UserDB
import com.seok.gitfordeveloper.room.model.Commit
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Response
import java.sql.Date

class MainActivity : AppCompatActivity() {

    private var userDb: UserDB? = null
    private var user = User()
    private var commitDb: CommitDB? = null
    private var commits = listOf<Commit>()
    private var commit = Commit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userDb = UserDB.getInstance(this)
        commitDb = CommitDB.getInstance(this)

        Thread(Runnable {
            tv_today_commit.text = "Today Commit : " + getTodayCommit(Date(System.currentTimeMillis()).toString())
        }).start()

        ApiUtils.getUserService().getUserInfo("token " + user.token)
            .enqueue(object : retrofit2.Callback<GithubApiUserInfo> {
                override fun onFailure(call: Call<GithubApiUserInfo>, t: Throwable) {
                    Log.d(this@MainActivity.localClassName, t.message)
                }

                override fun onResponse(call: Call<GithubApiUserInfo>, response: Response<GithubApiUserInfo>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        tv_user_id.text = body?.login
                        tv_github_url.text = body?.html_url
                        Glide.with(this@MainActivity).load(body?.avatar_url).into(user_img_profile)
                        doAsync { getCommits(body?.html_url.toString()) }
                    }
                }
            })
        getUserInfo()

    }

    // ToDoList Commit을 Mock 해서 테스트 하기
    private fun getTodayCommit(date: String): String {
        commit = commitDb?.commitDao()?.getToayCommit(date)!!
        return commit.commit.toString()
    }

    private fun getUserInfo() {
        Thread(Runnable {
            try {
                user = userDb?.userDao()?.getUser(BuildConfig.CLIENT_ID)!!

            } catch (e: Exception) {
                Log.e(this@MainActivity.localClassName, e.message)
            }
        }).start()
    }

    fun getCommits(url: String) {
        commits = commitDb?.commitDao()?.getAll()!!
        if (commits.isEmpty()) {
            val doc = Jsoup.connect(url).get()
            val regex =
                """data-count=\"\d+\" data-date=\"([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))\"""".toRegex()
            var i = 1
            try {
                while (doc.select("g").select("rect")[i].toString().startsWith("<rect class")) {
                    val matchResult: MatchResult? = regex.find(doc.select("g").select("rect")[i].toString())
                    val value = matchResult!!.value.replace("\"", "")
                    val split: List<String> = value.split(" ")
                    val commitCount: List<String> = split[0].split("=")
                    val commitDate: List<String> = split[1].split("=")
                    val commit = Commit()
                    commit.date = commitDate[1]
                    commit.commit = commitCount[1].toInt()
                    commitDb!!.commitDao()?.insert(commit)
                    i++
                }

            } catch (e: Exception) {

            }
        } else {
            runOnUiThread {
                var x = 0
                var y = 0
                for (i in 0 until commits.size) {
                    if (i % 7 == 0 && i != 0) {
                        x += 40
                        y = 0
                    }
                    var topTv1 = TextView(this@MainActivity)
                    topTv1.layoutParams =
                        LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ActionBar.LayoutParams.WRAP_CONTENT
                        )
                    topTv1.setTextColor(Color.parseColor("#000000"))
                    val count = commits[i].commit
                    val color = when {
                        count == 0 -> "#ebedf0"
                        count < 4 -> "#c6e48b"
                        count < 7 -> "#7bc96f"
                        count < 10 -> "#239a3b"
                        else -> "#196127"
                    }
                    topTv1.setBackgroundColor(Color.parseColor(color))
                    topTv1.textSize = 12f
                    topTv1.setPadding(2, 0, 2, 0)
                    topTv1.text = count.toString()
                    topTv1.x = x.toFloat()
                    topTv1.y = y.toFloat()
                    dynamicArea.addView(topTv1)
                    y += 40
                }
            }
        }
    }

    override fun onDestroy() {
        UserDB.destroyInstance()
        super.onDestroy()
    }
}