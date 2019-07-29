package com.seok.gitfordeveloper

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.HttpUrl

class LoginActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val httpUrl = HttpUrl.Builder()
            .scheme("https")
            .host("github.com")
            .addPathSegment("login")
            .addPathSegment("oauth")
            .addPathSegment("authorize")
            .addQueryParameter("client_id", getString(R.string.github_app_id))
//            .addQueryParameter("redirect_uri", App.redirect_uri)
//            .addQueryParameter("state", getRandomString())
            .addQueryParameter("scope", "user")
            .addQueryParameter("allow_signup","false")
            .build()
            www.loadUrl(httpUrl.toString())
//        login_github_btn.setOnClickListener {
//            www.loadUrl(httpUrl.toString())
//            //www.webViewClient = HelloWebViewClient()
//
//        }
        Log.d("astef2", Uri.parse(httpUrl.toString()).toString())

    }
}
class HelloWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        Log.d("astef3", url)
        return true

    }
}