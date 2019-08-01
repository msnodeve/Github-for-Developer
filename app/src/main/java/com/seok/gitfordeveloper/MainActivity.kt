package com.seok.gitfordeveloper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val okHttpClient = OkHttpClient()


        var requset = Request.Builder()
            .url("https://api.github.com/user")
            .header("Authorization", "token " + UserInfo.access_token)
            .build()
        okHttpClient.newCall(requset).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("dLog-main-onFailure", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody: String = response.body()!!.string()
                var jsonObject = JSONObject(responseBody)
                user_name.setText(jsonObject.getString("login"))
                user_mail.setText(jsonObject.getString("html_url"))
                runOnUiThread(object : Runnable {
                    override fun run() {
                        Glide.with(this@MainActivity)
                            .load(jsonObject.getString("avatar_url"))
                            .into(user_profile)
                    }
                })
            }
        })
    }
}