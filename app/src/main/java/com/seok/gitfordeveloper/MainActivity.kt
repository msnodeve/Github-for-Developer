package com.seok.gitfordeveloper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.seok.gitfordeveloper.retrofit.ApiUtils
import com.seok.gitfordeveloper.retrofit.models.GithubApiUserInfo
import com.seok.gitfordeveloper.room.model.User
import com.seok.gitfordeveloper.room.database.UserDB
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var userDb : UserDB? = null
    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userDb = UserDB.getInstance(this)

        getUserInfo()
        
    }

    private fun getUserInfo() {
        Thread(Runnable {
            try {
                user = userDb?.userDao()?.getUser(BuildConfig.CLIENT_ID)!!
                ApiUtils.getUserService().getUserInfo("token " + user.token)
                    .enqueue(object : retrofit2.Callback<GithubApiUserInfo>{
                        override fun onFailure(call: Call<GithubApiUserInfo>, t: Throwable) {
                            Log.d(this@MainActivity.localClassName, t.message)
                        }
                        override fun onResponse(call: Call<GithubApiUserInfo>, response: Response<GithubApiUserInfo>) {
                            if (response.isSuccessful) {
                                val body = response.body()
                                tv_user_id.text = body?.login
                                tv_github_url.text = body?.html_url
                                Glide.with(this@MainActivity).load(body?.avatar_url).into(user_img_profile)
                            }
                        }
                    })
            } catch (e: Exception) {
                Log.e(this@MainActivity.localClassName, e.message)
            }
        }).start()

    }

    override fun onDestroy() {
        UserDB.destroyInstance()
        super.onDestroy()
    }
}