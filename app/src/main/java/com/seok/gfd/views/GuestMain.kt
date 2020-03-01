package com.seok.gfd.views

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.seok.gfd.R
import kotlinx.android.synthetic.main.activity_guest_main.*
import java.util.*

class GuestMain : AppCompatActivity() {
    private val arrayList =ArrayList<String>(listOf("First Element", "Second Element", "Third Element", "Fourth Element", "Fifth Element"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_main)

        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, arrayList)
        listView.adapter = adapter

        simpleSwipeRefreshLayout.setOnRefreshListener {
            simpleSwipeRefreshLayout.isRefreshing = false
            shuffleItems()
        }
    }

    private fun shuffleItems(){
        arrayList.shuffle(Random(System.currentTimeMillis()))
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, arrayList)
        listView.adapter = adapter
    }

}
