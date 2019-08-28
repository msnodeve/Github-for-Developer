package com.seok.gitfordeveloper.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.seok.gitfordeveloper.BuildConfig
import com.seok.gitfordeveloper.views.MainActivity

class SharedPreference(application: Application) {
    private val pref = application.getSharedPreferences(
        BuildConfig.PREFERENCES_FILE,
        AppCompatActivity.MODE_PRIVATE
    )
    private val editor = pref.edit()
    fun checkForToken(token: String): Boolean {
        return pref.contains(token)
    }

    fun getToken(token : String): String {
        return pref.getString(token, null)
    }
    fun editToken(key : String, token : String){
        editor.putString(key, token)
        editor.commit()
    }
}