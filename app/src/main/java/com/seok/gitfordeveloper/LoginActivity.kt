package com.seok.gitfordeveloper

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.seok.gitfordeveloper.room.model.User
import com.seok.gitfordeveloper.room.database.UserDB
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import java.io.IOException
import java.net.HttpURLConnection

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class LoginActivity : AppCompatActivity() {

    private  lateinit var authGithub: AuthGithub

    private var userDb : UserDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        authGithub = AuthGithub()
        userDb = UserDB.getInstance(this)

        login_img_login.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(
                authGithub.buildHttpUrl(BuildConfig.CLIENT_ID)))
            startActivityForResult(intent, HttpURLConnection.HTTP_OK)
        }
    }

    override fun onNewIntent(intent: Intent?){
        super.onNewIntent(intent)
        val uri = intent!!.data
        val authGithub = AuthGithub()
        val code = authGithub.getCode(uri.toString())
        sendPost(code)
    }

    private fun sendPost(code : String){
        val request = makeRequest(code)
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val accessToken = authGithub.getToken(response.body()!!.string())
                Thread(Runnable {
                    val user = User()
                    user.mac = BuildConfig.CLIENT_ID
                    user.token = accessToken
                    userDb?.userDao()?.insert(user)
                    next()
                }).start()
            }
            override fun onFailure(call: Call, e: IOException) {
                Log.d(LoginActivity::getLocalClassName.toString(), e.toString())
            }
        })

    }
    private fun makeRequest(code : String) :Request{
        val form = FormBody.Builder()
            .add("client_id", BuildConfig.CLIENT_ID)
            .add("client_secret", BuildConfig.CLIENT_SECRET)
            .add("code", code)
            .build()
        return Request.Builder()
            .url("https://github.com/login/oauth/access_token")
            .post(form)
            .build()
    }
    private fun next(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}
