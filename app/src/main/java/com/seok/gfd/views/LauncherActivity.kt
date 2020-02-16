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

//        val scalablelayout = ScalableLayout(this,commonUtils.getScreenWidth(), commonUtils.getScreenHeight())
//        scalablelayout.backgroundColor = Color.LTGRAY
//        val tv = TextView(this)
//        tv.text = "testjfalskdflasjdlkfjaskldfjlksadjfklsajdflksjdlkfjaslkdfjlasdflasdjflkasdf"
//        tv.backgroundColor = Color.YELLOW
//        scalablelayout.addView(tv, 10f, 10f, commonUtils.getScreenWidth()/2, commonUtils.getScreenHeight())
//        scalablelayout.setScale_TextSize(tv, commonUtils.getScreenHeight()/10)

//        scalablelayout.setScale_TextSize(tv, 20f)
//        val imageVIew = ImageView(this)
//        imageVIew.setImageResource(R.drawable.gfd_logo)
//        scalablelayout.addView(imageVIew, 200f, 30f, 50f, 50f)

//        launcher_const_layout.addView(scalablelayout)


        startLoading()
    }

    private fun startLoading(){
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }, 100)
    }

    private fun initSetUI(){
        commonUtils = CommonUtils.instance

        // 디스플레이 해상도 가져오기
        val display = windowManager.defaultDisplay
        commonUtils.setScreenHeight(display.height.toFloat())
        commonUtils.setScreenWidth(display.width.toFloat())
    }
}
