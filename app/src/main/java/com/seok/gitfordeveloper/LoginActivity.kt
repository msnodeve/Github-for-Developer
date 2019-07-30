package com.seok.gitfordeveloper

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.HttpUrl


class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_github_btn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        var httpUrl :HttpUrl = HttpUrl.Builder()
            .scheme("https")
            .host("github.com")
            .addPathSegment("login")
            .addPathSegment("oauth")
            .addPathSegment("authorize")
            .addQueryParameter("client_id", getString(R.string.github_app_id))
            .addQueryParameter("scope", "user:email")
            .build()
        var intent : Intent = Intent(Intent.ACTION_VIEW, Uri.parse(httpUrl.toString()))
        Log.d("testtest", httpUrl.toString())
        startActivityForResult(intent, StatusCode.REQUEST_GITHUB_REDIRECT)
    }
}