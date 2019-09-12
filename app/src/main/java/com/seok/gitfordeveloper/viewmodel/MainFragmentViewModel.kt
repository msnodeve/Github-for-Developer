package com.seok.gitfordeveloper.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seok.gitfordeveloper.BuildConfig
import com.seok.gitfordeveloper.R
import com.seok.gitfordeveloper.database.CommitsDatabase
import com.seok.gitfordeveloper.retrofit.RetrofitClient
import com.seok.gitfordeveloper.retrofit.domain.User
import com.seok.gitfordeveloper.retrofit.response.UserRepository
import com.seok.gitfordeveloper.utils.AuthUserToken
import com.seok.gitfordeveloper.utils.SharedPreferencesForUser
import com.seok.gitfordeveloper.utils.ValidationCheck
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import retrofit2.Call
import retrofit2.Response
import java.net.HttpURLConnection

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application
    private val userRepository = UserRepository(application)
    private val validationCheck = ValidationCheck(application)
    private val commitsDatabase = CommitsDatabase.getInstance(application)
    private val sharedPreferencesForUser = SharedPreferencesForUser(application)
    private val authUserToken = AuthUserToken(application)



    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _existCommit = MutableLiveData<Boolean>()
    val existCommit: LiveData<Boolean>
        get() = _existCommit


    fun githubUserApi(token: String): LiveData<User> {
        return userRepository.getUserMutableData(token)
    }

    fun checkCommit() {
        GlobalScope.launch {
            val query = async(Dispatchers.IO) {
                val today = validationCheck.existTodayCommit()
                try {
                    commitsDatabase.commitsDatabaseDao().getCommits(today).dataCount
                } catch (e: NullPointerException) {
                    Log.e(this.javaClass.simpleName, e.message.toString())
                    _existCommit.postValue(true)
                }
            }
            query.await()
        }
    }

    fun setUserInfo() {
        if (sharedPreferencesForUser.checkUserInfo(app.getString(R.string.user_id))) {
            val user = User(
                sharedPreferencesForUser.getValue(app.getString(R.string.user_id)),
                sharedPreferencesForUser.getValue(app.getString(R.string.user_url)),
                sharedPreferencesForUser.getValue(app.getString(R.string.user_image))
            )
            _user.postValue(user)
        }else{
            val userService = RetrofitClient.githubUserApiService()
            val call = userService.githubUserApi("token " + authUserToken.getToken(BuildConfig.PREFERENCES_TOKEN_KEY))
            call.enqueue(object : retrofit2.Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val body = response.body()!!
                    _user.postValue(User(
                        body.login, body.html_url, body.avatar_url, body.id, HttpURLConnection.HTTP_OK
                    ))
                    sharedPreferencesForUser.setKeyValue(app.getString(R.string.user_id), body.login)
                    sharedPreferencesForUser.setKeyValue(app.getString(R.string.user_url), body.html_url)
                    sharedPreferencesForUser.setKeyValue(app.getString(R.string.user_image), body.avatar_url)
                    sharedPreferencesForUser.setKeyValue(app.getString(R.string.user_gid), body.id.toString())
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e(this.javaClass.simpleName, t.message.toString())
                }
            })
        }
    }
}
