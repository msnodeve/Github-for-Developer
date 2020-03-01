package com.seok.gfd.views

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.seok.gfd.R
import kotlinx.android.synthetic.main.activity_guest_main.*
import java.util.*

class GuestMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_main)

        simpleSwipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({
                simpleSwipeRefreshLayout.isRefreshing = false
                val r = Random()
                val i1 : Int = r.nextInt(100)
                guestTextView.text = i1.toString()
            }, 3000)
        }
    }

}
