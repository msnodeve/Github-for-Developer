package com.seok.gfd.utils

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.seok.gfd.BuildConfig
import com.seok.gfd.R
import okhttp3.HttpUrl
import java.net.URI

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AuthUserToken(private val application: Application) {
    private val pref = application.getSharedPreferences(
        BuildConfig.PREFERENCES_FILE,
        AppCompatActivity.MODE_PRIVATE
    )
    private val editor = pref.edit()

    fun getToken(token : String) : String{
        return if(pref.contains(token)) {
            this.pref.getString(token, null)
        }else{
            application.getString(R.string.no_token)
        }
    }

    fun editToken(key : String, token : String){
        editor.putString(key, token)
        editor.commit()
    }

    fun getCode(uri : String) : String{
        return URI.create(uri).query.split("=")[1]
    }
}