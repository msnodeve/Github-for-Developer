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
import com.seok.gitfordeveloper.room.database.UsersDB
import com.seok.gitfordeveloper.room.model.Commit
import com.seok.gitfordeveloper.room.model.UserInfo
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Response
import java.sql.Date

class MainActivity : AppCompatActivity() {

    private var usersDb: UsersDB? = null
    private var commitDb: CommitDB? = null

    private var user = User()
    private var commits = listOf<Commit>()
    private var commit = Commit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersDb = UsersDB.getInstance(this)
        commitDb = CommitDB.getInstance(this)

//        Thread(Runnable {
//            usersDb?.userDao()?.deleteAll()
//            usersDb?.userInfoDao()?.deleteAll()
//            commitDb?.commitDao()?.deleteAll()
//        }).start()
        Thread(Runnable {
            try {
                user = usersDb?.userDao()?.getUser(BuildConfig.CLIENT_ID)!!
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

            } catch (e: NullPointerException) {

            }
        }).start()
        doAsync { setUI() }
    }

    private fun setUI() {
        commits = commitDb?.commitDao()?.getAll()!!
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
            try {
                tv_today_commit.text = "Today commit : ${commits[commits.size - 1].commit}"
            } catch (e: ArrayIndexOutOfBoundsException) {
                tv_today_commit.text = "Today commit : 0"
                Log.e(this@MainActivity.localClassName, e.message.toString())
            }
        }
    }

    fun getCommits(url: String) {
        val doc = Jsoup.connect(url).get()
        val regex =
            """data-count=\"\d+\" data-date=\"([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))\"""".toRegex()
        var i = 1
        try {
            while (doc.select("g").select("rect")[i].toString().startsWith("<rect class")) {
                val matchResult: MatchResult? = regex.find(doc.select("g").select("rect")[i++].toString())
                val value = matchResult!!.value.replace("\"", "")
                val split: List<String> = value.split(" ")
                val commitCount: List<String> = split[0].split("=")
                val commitDate: List<String> = split[1].split("=")
                val commit = Commit()
                commit.date = commitDate[1]
                commit.commit = commitCount[1].toInt()
                commitDb!!.commitDao()?.insert(commit)
            }
        } catch (e: Exception) {

        }
        doAsync { setUI() }
    }


    override fun onDestroy() {
        UsersDB.destroyInstance()
        super.onDestroy()
    }
}