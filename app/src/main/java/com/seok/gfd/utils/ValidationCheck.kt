package com.seok.gfd.utils

import android.app.Application
import java.text.SimpleDateFormat
import java.util.*

class ValidationCheck(application: Application) {

    fun existTodayCommit() : String{
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date(System.currentTimeMillis())
        return formatter.format(date)
    }
}