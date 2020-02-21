package com.seok.gfd.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.seok.gfd.R
import com.seok.gfd.utils.CommonUtils

class LauncherActivity : AppCompatActivity() {

    private lateinit var commonUtils : CommonUtils

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initSetUI()
        startLoading()
    }

    private fun startLoading(){
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }, 1500)
    }

    private fun initSetUI(){
        commonUtils = CommonUtils.instance

        // 디스플레이 해상도 가져오기
        val display = windowManager.defaultDisplay
        commonUtils.setScreenHeight(display.height.toFloat())
        commonUtils.setScreenWidth(display.width.toFloat())
    }
}
