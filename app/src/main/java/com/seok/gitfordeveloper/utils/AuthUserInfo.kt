package com.seok.gitfordeveloper.utils

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.seok.gitfordeveloper.BuildConfig
import com.seok.gitfordeveloper.R

class AuthUserInfo(private val application: Application) {
    private val pref = application.getSharedPreferences(
        BuildConfig.PREFERENCES_FILE,
        AppCompatActivity.MODE_PRIVATE
    )
    private val editor = pref.edit()

    fun getUser(keyId: String, keyEmail: String, keyImage: String): Boolean {
        val userId = getUserId(keyId) != application.getString(R.string.no_user_id)
        val userEmail = getUserUrl(keyEmail) != application.getString(R.string.no_user_email)
        val userImage = getUserImage(keyImage) != application.getString(R.string.no_user_image)
        return userId && userEmail && userImage
    }

    fun getUserId(userId: String): String {
        return if (pref.contains(userId)) {
            this.pref.getString(userId, null)
        } else {
            application.getString(R.string.no_user_id)
        }
    }

    fun getUserUrl(userEmail: String): String {
        return if (pref.contains(userEmail)) {
            this.pref.getString(userEmail, null)
        } else {
            application.getString(R.string.no_user_email)
        }
    }

    fun getUserImage(userImage: String): String {
        return if (pref.contains(userImage)) {
            this.pref.getString(userImage, null)
        } else {
            application.getString(R.string.no_user_image)
        }
    }
    fun setKeyValue(key:String, value : String){
        editor.putString(key, value)
        editor.commit()
    }
    fun setUser(userId: String, userEmail: String, userImage: String) {
        editor.putString(application.getString(R.string.user_id), userId)
        editor.putString(application.getString(R.string.user_url), userEmail)
        editor.putString(application.getString(R.string.user_image), userImage)
        editor.commit()
    }
}