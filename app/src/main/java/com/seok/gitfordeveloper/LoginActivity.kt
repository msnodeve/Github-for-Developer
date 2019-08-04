package com.seok.gitfordeveloper

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.HttpUrl
import java.net.HttpURLConnection

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_img_login.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(buildHttpUrl()))
            startActivityForResult(intent, HttpURLConnection.HTTP_OK)
        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 발급 받은 code 토큰으로 엑세스 토큰을 받아올 것
        if (intent != null) {

        }
    }
    private fun buildHttpUrl(): String{
        return HttpUrl.Builder()
            .scheme("https")
            .host("github.com")
            .addPathSegment("login")
            .addPathSegment("oauth")
            .addPathSegment("authorize")
            .addQueryParameter("client_id", BuildConfig.CLIENT_ID)
            .build().toString()
    }
}
