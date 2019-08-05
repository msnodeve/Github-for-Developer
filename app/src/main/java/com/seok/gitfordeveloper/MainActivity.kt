package com.seok.gitfordeveloper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.seok.gitfordeveloper.room.User
import com.seok.gitfordeveloper.room.UserDB
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var userDb : UserDB? = null
    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userDb = UserDB.getInstance(this)

        Thread(Runnable {
            try {
                user = userDb?.userDao()?.getUser(BuildConfig.CLIENT_ID)!!

                Log.d("testtest", user.mac + " > " + user.token + " > " + user.creationDate)

            } catch (e: Exception) {
                Log.d("tag", "Error - $e")
            }
        }).start()
    }
    override fun onDestroy() {
        UserDB.destroyInstance()
        super.onDestroy()
    }
}
