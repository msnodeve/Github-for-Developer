package com.seok.gitfordeveloper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.seok.gitfordeveloper.retrofit.ApiUtils
import com.seok.gitfordeveloper.retrofit.RetrofitClient
import com.seok.gitfordeveloper.retrofit.models.UserInfoService
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                        var body: UserInfoService
                        body = response.body()!!
                        user_name.setText(body.login)
                        user_mail.setText(body.html_url)
                        Glide.with(this@MainActivity).load(body.avatar_url).into(user_profile)
                    }
                }
            })
    }
}