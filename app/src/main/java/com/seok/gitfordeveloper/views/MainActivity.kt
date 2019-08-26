package com.seok.gitfordeveloper.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.seok.gitfordeveloper.R
import com.seok.gitfordeveloper.retrofit.ApiUtils
import com.seok.gitfordeveloper.retrofit.domain.GithubUser
import com.seok.gitfordeveloper.room.database.CommitsDB
import com.seok.gitfordeveloper.room.model.User
import com.seok.gitfordeveloper.room.database.UsersDB
import com.seok.gitfordeveloper.room.model.Commits
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var usersDb: UsersDB? = null
    private var commitDb: CommitsDB? = null
    private var user = User("","",true,"")
    private var commits = listOf<Commits>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this, getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)


        adView.adListener = object : AdListener(){
            override fun onAdImpression() {
                super.onAdImpression()
            }

            override fun onAdLeftApplication() {
                super.onAdLeftApplication()
            }

            override fun onAdClicked() {
                super.onAdClicked()
            }

            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
            }

            override fun onAdClosed() {
                super.onAdClosed()
            }

            override fun onAdOpened() {
                super.onAdOpened()
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
            }
        }
        usersDb = UsersDB.getInstance(this)
        commitDb = CommitsDB.getInstance(this)

        longToast("로딩 중 입니다.\n잠시만 기다려주세요.")

        Thread(Runnable {
            try {
                val usersDb1 = usersDb
//                user = (if (usersDb1 != null) usersDb1.userDao() else null)?.getUser(BuildConfig.CLIENT_ID)!!
                ApiUtils.getUserService().getGithubApi("token " + user.token)
                    .enqueue(object : retrofit2.Callback<GithubUser> {
                        override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                            Log.d(this@MainActivity.localClassName, t.message)
                        }

                        override fun onResponse(call: Call<GithubUser>, response: Response<GithubUser>) {
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
        val maxCommit = commitDb?.commitDao()?.getMaxCommit()?.commits
        runOnUiThread {
            contribute.removeAllViews()
            contribute.columnCount = commits.size / 7 + 1
            contribute.rowCount = 7
            for (i in 0 until commits.size) {
                val layout = LinearLayout(this@MainActivity)
                val param = LinearLayout.LayoutParams(65, 65)
                param.margin = 4
                layout.layoutParams = param
                val txt = TextView(this@MainActivity)
                txt.text = commits[i].commits.toString()
                layout.gravity = Gravity.CENTER
                layout.addView(txt)
                val count = commits[i].commits
                layout.backgroundColor = resources.getColor(
                    when {
                        count == 0 -> R.color.nonCommit
                        count < maxCommit!! / 4 -> R.color.stCommit
                        count < maxCommit!! / 2 -> R.color.ndCommit
                        count < maxCommit!! / 8 * 5 -> R.color.thCommit
                        else -> R.color.fiCommit
                    }
                )
                contribute.addView(layout)
            }
            try {
                tv_today_commit.text = "Today commits : ${commits[commits.size - 1].commits}"
            } catch (e: ArrayIndexOutOfBoundsException) {
                tv_today_commit.text = "Today commits : 0"
                Log.e(this@MainActivity.localClassName, e.message.toString())
            }
        }
        scroll_contribute.smoothScrollTo(contribute.width, contribute.height)
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
                val commit = Commits()
                commit.date = commitDate[1]
                commit.commits = commitCount[1].toInt()
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