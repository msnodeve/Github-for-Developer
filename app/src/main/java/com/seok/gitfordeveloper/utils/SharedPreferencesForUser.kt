package com.seok.gitfordeveloper.utils

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.seok.gitfordeveloper.BuildConfig
import com.seok.gitfordeveloper.R

class SharedPreferencesForUser(private val application: Application) {
    private val pref = application.getSharedPreferences(
        BuildConfig.PREFERENCES_FILE,
        AppCompatActivity.MODE_PRIVATE
    )
    private val editor = pref.edit()

    fun setKeyValue(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getValue(key: String): String {
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        return pref.getString(key, application.getString(R.string.no_user_key))
    }

    fun checkUserInfo(key: String): Boolean {
        return pref.contains(key)
    }
}