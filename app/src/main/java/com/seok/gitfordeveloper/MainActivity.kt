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
import java.lang.IndexOutOfBoundsException

class MainActivity : AppCompatActivity() {

    lateinit var cbList: ArrayList<ContributeData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cbList = ArrayList()

        doAsync {
            val doc = Jsoup.connect("https://github.com/msnodeve").get()
            val regex =
                """data-count=\"\d+\" data-date=\"([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))\"""".toRegex()
            var i = 1
            try {
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
            }catch (e : IndexOutOfBoundsException){
                Log.d("dLog-main-err", e.toString())
            }
            setContribute()

        }

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
    }
    fun setContribute(){
        runOnUiThread(object : Runnable{
            override fun run() {
                var x = 0
                var y = 0
                for (i in 0..cbList.size - 1) {
                    if (i % 7 == 0 && i != 0) {
                        x += 40
                        y = 0
                    }
                    var topTv1 = TextView(this@MainActivity)
                    topTv1.layoutParams =
                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
                    topTv1.setTextColor(Color.parseColor("#000000"))
                    var count = cbList[i].dataCount
                    if(count ==0){
                        topTv1.setBackgroundColor(Color.parseColor("#ebedf0"))
                    }else if(count < 4){
                        topTv1.setBackgroundColor(Color.parseColor("#c6e48b"))
                    }else if(count < 7){
                        topTv1.setBackgroundColor(Color.parseColor("#7bc96f"))
                    }else if(count < 10){
                        topTv1.setBackgroundColor(Color.parseColor("#239a3b"))
                    }else{
                        topTv1.setBackgroundColor(Color.parseColor("#196127"))
                    }
                    topTv1.setTextSize(12f)
                    topTv1.setPadding(2, 0, 2, 0)
                    topTv1.setText(cbList[i].dataCount.toString())
                    topTv1.x = x.toFloat()
                    topTv1.y = y.toFloat()
                    dynamicArea.addView(topTv1)
                    y += 40
                }
            }
        })
    }
}

data class ContributeData(
    var dataDate: String,
    var dataCount: Int
)