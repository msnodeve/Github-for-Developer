package com.seok.gfd.views

import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.seok.gfd.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setAnimation()
    }

    private fun setAnimation() {
        val bottomToTop = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top)
        search_txt_info2.startAnimation(bottomToTop)
        val topToBottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom)
        topToBottom.startOffset = 300
        search_txt_info1.startAnimation(topToBottom)
        val leftToRight = AnimationUtils.loadAnimation(this, R.anim.left_to_right)
        leftToRight.startOffset = 800
        search_layout_id.startAnimation(leftToRight)
    }
}