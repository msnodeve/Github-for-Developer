package com.seok.gfd.utils

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.seok.gfd.BuildConfig
import com.seok.gfd.R
import com.seok.gfd.retrofit.domain.User

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

    fun setValueObject(user:User){
        val gson = GsonBuilder().create()
        val userInfoJson = gson.toJson(user, User::class.java)
        editor.putString(application.getString(R.string.user_info), userInfoJson)
        editor.commit()


        val t = pref.getString(application.getString(R.string.user_info), null)
        val convertUser = gson.fromJson(t, User::class.java)
        // 객체 json화 저장 및 변환
        // 저장 끝내기
        convertUser
    }
}