package com.seok.gfd.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.seok.gfd.R
import com.seok.gfd.adapter.CustomAdapter
import kotlinx.android.synthetic.main.activity_guest_main.*
import java.util.*

class GuestMain : AppCompatActivity() {
    private val personNames =ArrayList(listOf("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7", "Person 8", "Person 9", "Person 10", "Person 11", "Person 12", "Person 13", "Person 14"))
    private val personImages = ArrayList(listOf(R.drawable.btn_bottom_help, R.drawable.icon_location, R.drawable.btn_bottom_home, R.drawable.btn_bottom_rank, R.drawable.gfd_app_logo, R.drawable.gfd_launcher, R.drawable.gfd_logo_background, R.drawable.gfd_logo_foreground, R.drawable.github_login_users_background, R.drawable.guest_login_background, R.drawable.ic_launcher_background, R.drawable.icon_bio, R.drawable.rounding_trans_background, R.drawable.rounding_background))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_main)

        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager
        val customAdapter = CustomAdapter(personNames, personImages, this)
        recyclerView.adapter = customAdapter

        simpleSwipeRefreshLayout.setOnRefreshListener {
            simpleSwipeRefreshLayout.isRefreshing = false
            shuffleItems()
        }
    }

    private fun shuffleItems(){
        personNames.shuffle(Random(System.currentTimeMillis()))
        personImages.shuffle(Random(System.currentTimeMillis()))
        val customAdapter = CustomAdapter(personNames, personImages, this)
        recyclerView.adapter = customAdapter
    }

}
