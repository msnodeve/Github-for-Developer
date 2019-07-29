package com.seok.gitfordeveloper

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this,MainActivity::class.java))
        }, 2000)
        finish()
    }
}
