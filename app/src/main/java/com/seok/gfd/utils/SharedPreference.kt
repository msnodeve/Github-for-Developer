package com.seok.gfd.utils

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.seok.gfd.BuildConfig
import com.seok.gfd.R

class SharedPreference(private val application: Application) {
    private val pref = application.getSharedPreferences(
        BuildConfig.PREFERENCES_FILE,
        AppCompatActivity.MODE_PRIVATE
    )

    private val editor = pref.edit()

    fun setValue(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getValue(key: String): String {
        return if(pref.contains(key)){
            @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            pref.getString(key, application.getString(R.string.no_token))
        }else{
            application.getString(R.string.no_token)
        }
    }
}