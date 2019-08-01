package com.seok.gitfordeveloper

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.seok.gitfordeveloper.github.AuthGithub
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var authGithub: AuthGithub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authGithub = AuthGithub(this)

        login_github_btn.setOnClickListener {
            var intent = authGithub.redirectURLActivity()
            startActivityForResult(intent, StatusCode.REQUEST_GITHUB_REDIRECT)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 발급 받은 code 토큰으로 엑세스 토큰을 받아올 것
        if (intent != null) {
            var code: String = intent.getStringExtra("code")
            if (code != null) {
                authGithub.sendPost(code)
            } else {
                Log.d("dLog-Login-onNewIntent", "get code err")
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}