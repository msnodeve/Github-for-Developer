package com.seok.gitfordeveloper

import android.app.ActionBar
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.seok.gitfordeveloper.retrofit.ApiUtils
import com.seok.gitfordeveloper.retrofit.models.UserInfoService
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {

    lateinit var cbList: ArrayList<ContributeData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cbList = ArrayList()

        ApiUtils.getUserService().getUserInfo("token " + UserInfo.access_token.toString())
            .enqueue(object : retrofit2.Callback<UserInfoService> {
                override fun onFailure(call: retrofit2.Call<UserInfoService>, t: Throwable) {
                    Log.d("dLog-main-onFailure", t.toString())
                }

                override fun onResponse(
                    call: retrofit2.Call<UserInfoService>,
                    response: retrofit2.Response<UserInfoService>
                ) {
                    if (response.isSuccessful) {
                        var body = response.body()!!
                        user_name.setText(body.login)
                        user_mail.setText(body.html_url)
                        Glide.with(this@MainActivity).load(body.avatar_url).into(user_profile)
                    }
                }
            })

        doAsync {
            val doc = Jsoup.connect("https://github.com/msnodeve").get()
            val regex =
                """data-count=\"\d+\" data-date=\"([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))\"""".toRegex()
            var i = 1
            while (doc.select("g").select("rect")[i] != null) {
                val gTag = doc.select("g").select("rect")[i]
                val matchResult: MatchResult? = regex.find(gTag.toString())
                var value = matchResult!!.value.replace("\"", "")
                var split: List<String> = value.split(" ")
                var result1: List<String> = split[0].split("=")
                var result2: List<String> = split[1].split("=")
                cbList.add(ContributeData(result2[1], result1[1].toInt()))
                Log.d("dLog-main-doAsync", result1[1] + " : " + result2[1])
                i++
            }
        }
        var x = 0
        var y = 0
        button.setOnClickListener {
            for (i in 0..cbList.size - 1) {
                if (i % 7 == 0 && i != 0) {
                    x += 30
                    y = 0
                }
                var topTv1 = TextView(this)
                topTv1.layoutParams =
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
                topTv1.setBackgroundColor(Color.parseColor("#00FFFFFF"))
                topTv1.setTextColor(Color.parseColor("#000000"))
                topTv1.setTextSize(11f)
                topTv1.setPadding(2, 0, 2, 0)
                topTv1.setText(cbList[i].dataCount.toString())
                topTv1.x = x.toFloat()
                topTv1.y = y.toFloat()
                dynamicArea.addView(topTv1)
                y += 50
            }
        }
    }
}

data class ContributeData(
    var dataDate: String,
    var dataCount: Int
)