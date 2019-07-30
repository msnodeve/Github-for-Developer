package com.seok.gitfordeveloper.unit

import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.seok.gitfordeveloper.LoginActivity
import com.seok.gitfordeveloper.callback.GithubCallback
import okhttp3.HttpUrl
import kotlin.math.log

class GithubWebViewClient(str : String) : WebViewClient() {

    private var httpUrl : HttpUrl
    init {
        httpUrl = HttpUrl.Builder().scheme("https")
            .host("github.com")
            .addPathSegment("login")
            .addPathSegment("oauth")
            .addPathSegment("authorize")
            .addQueryParameter("client_id", str)
            .addQueryParameter("scope", "user")
            .addQueryParameter("allow_signup","false")
            .build()
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        var uri : Uri = Uri.parse(url)
        if(uri.getQueryParameter("code") != null){
            Log.d("testtest", uri.getQueryParameter("code"))
        }else{
            Log.d("testtest", uri.toString())
        }
        return super.shouldOverrideUrlLoading(view, url)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
    }

    fun getHttpUrl() : String{
        return httpUrl.toString()
    }
}