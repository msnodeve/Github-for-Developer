package com.seok.gitfordeveloper

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.HttpUrl
import java.net.HttpURLConnection

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class LoginActivity : AppCompatActivity() {

    private  lateinit var authGithub: AuthGithub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        authGithub = AuthGithub()

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
    }
}
