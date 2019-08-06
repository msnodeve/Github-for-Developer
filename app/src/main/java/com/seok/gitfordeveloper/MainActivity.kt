package com.seok.gitfordeveloper

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.Gravity
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
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.margin
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

    private fun test() {
        for (i in 0 until (53 * 7)) {
            val layout = LinearLayout(this)
            var param = LinearLayout.LayoutParams(65,65)
            param.margin = 4
            layout.layoutParams = param
            val txt = TextView(this)
            txt.text = i.toString()
            layout.gravity = Gravity.CENTER
            layout.addView(txt)
            layout.backgroundColor = R.color.nonCommit
            contribute.addView(layout)
        }
    }

    private fun setUI() {
        commits = commitDb?.commitDao()?.getAll()!!
        runOnUiThread {
            contribute.columnCount = 53
            contribute.rowCount = 7
            for (i in 0 until commits.size) {
                Log.d("testtest", commits[i].date)
                val layout = LinearLayout(this)
                var param = LinearLayout.LayoutParams(65,65)
                param.margin = 4
                layout.layoutParams = param
                val txt = TextView(this)
                txt.text = commits[i].commit.toString()
                layout.gravity = Gravity.CENTER
                layout.addView(txt)
                val count = commits[i].commit
                layout.backgroundColor = resources.getColor(when {
                    count == 0 -> R.color.nonCommit
                    count < 4 -> R.color.stCommit
                    count < 7 -> R.color.ndCommit
                    count < 10 -> R.color.thCommit
                    else -> R.color.fiCommit
                })
                contribute.addView(layout)
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
        // 새로 초기화해줘야할 것같다.
    }


    override fun onDestroy() {
        UsersDB.destroyInstance()
        super.onDestroy()
    }
}