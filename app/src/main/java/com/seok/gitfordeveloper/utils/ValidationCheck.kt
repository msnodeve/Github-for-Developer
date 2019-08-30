package com.seok.gitfordeveloper.utils

import android.app.Application
import java.text.SimpleDateFormat
import java.util.*

class ValidationCheck(application: Application) {

    fun existTodayCommit(dataDate: String) : Boolean{
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date(System.currentTimeMillis())
        val today = formatter.format(date)
        return today== dataDate
    }
}