package com.seok.gitfordeveloper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle

class RedirectedActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri : Uri = intent.data
        if(validateUri(uri)){
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("code", uri.getQueryParameter("code"))
            startActivity(intent)
            finish()
        }
    }
    private fun validateUri(uri: Uri) :Boolean{
        return uri.toString().startsWith(BuildConfig.CALLBACK_URL)
    }
}