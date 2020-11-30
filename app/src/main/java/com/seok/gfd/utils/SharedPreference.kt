package com.seok.gfd.utils

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.seok.gfd.BuildConfig
import com.seok.gfd.R
import com.seok.gfd.retrofit.domain.User

class SharedPreference(private val application: Application) {
    private val pref = application.getSharedPreferences(
        "",
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

    fun setValueObject(key: String, user:User){
        val gson = GsonBuilder().create()
        // 객체 -> json 저장
        val userInfoJson = gson.toJson(user, User::class.java)
        editor.putString(key, userInfoJson)
        editor.commit()
    }

    fun getValueObject(key:String):User{
        val gson = GsonBuilder().create()
        val user = pref.getString(key, null)
        // json -> 객체 변환
        return gson.fromJson(user, User::class.java)
    }
}