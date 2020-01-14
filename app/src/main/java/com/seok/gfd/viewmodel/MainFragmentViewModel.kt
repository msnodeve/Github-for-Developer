package com.seok.gfd.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seok.gfd.BuildConfig
import com.seok.gfd.R
import com.seok.gfd.database.Commits
import com.seok.gfd.database.CommitsDatabase
import com.seok.gfd.retrofit.RetrofitClient
import com.seok.gfd.retrofit.domain.User
import com.seok.gfd.utils.AuthUserToken
import com.seok.gfd.utils.SharedPreferencesForUser
import com.seok.gfd.utils.ValidationCheck
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import retrofit2.Call
import retrofit2.Response
import java.net.HttpURLConnection

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application
    private val validationCheck = ValidationCheck(application)
    private val commitsDatabase = CommitsDatabase.getInstance(application)
    private val sharedPreferencesForUser = SharedPreferencesForUser(application)
    private val authUserToken = AuthUserToken(application)



    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _commits = MutableLiveData<List<Commits>>()
    val commits: LiveData<List<Commits>>
        get() = _commits

    private val _commit = MutableLiveData<Commits>()
    val commit : LiveData<Commits>
        get() = _commit

    private val _completeGetAllCommits = MutableLiveData<Boolean>()
    val completeGetAllCommits : LiveData<Boolean>
        get() = _completeGetAllCommits

    fun checkCommit() {
        GlobalScope.launch {
            val query = async(Dispatchers.IO) {
                val today = validationCheck.existTodayCommit()
                try {
                    commitsDatabase.commitsDatabaseDao().getCommits(today).dataCount
                } catch (e: NullPointerException) {
                    Log.e(this.javaClass.simpleName, e.message.toString())
                }
            }
            query.await()
        }
    }

    fun completeGetAllCommits(){
        _completeGetAllCommits.postValue(true)
    }

    private fun getAllCommit(){
        doAsync {
            val data = commitsDatabase.commitsDatabaseDao().getYearCommits().asReversed()
            _commits.postValue(data)
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
            getAllCommit()
        }else{
            val userService = RetrofitClient.githubApiService()
            val call = userService.getUserInfoFromGithubApi("token " + authUserToken.getToken(BuildConfig.PREFERENCES_TOKEN_KEY))
            call.enqueue(object : retrofit2.Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val body = response.body()!!
                    sharedPreferencesForUser.setKeyValue(app.getString(R.string.user_id), body.login)
                    sharedPreferencesForUser.setKeyValue(app.getString(R.string.user_url), body.html_url)
                    sharedPreferencesForUser.setKeyValue(app.getString(R.string.user_image), body.avatar_url)
                    sharedPreferencesForUser.setKeyValue(app.getString(R.string.user_gid), body.id.toString())
                    _user.postValue(User(
                        body.login, body.html_url, body.avatar_url, body.id, HttpURLConnection.HTTP_OK
                    ))
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e(this.javaClass.simpleName, t.message.toString())
                }
            })
        }
    }
    private fun setCommits(commits: ArrayList<Commits>) {
        doAsync {
            for (commit in commits) {
                commitsDatabase.commitsDatabaseDao().insert(commit)
            }
        }
    }
    fun getCommitsFromGithub(){
        doAsync {
            val doc = Jsoup.connect(sharedPreferencesForUser.getValue(app.getString(R.string.user_url))).get()
            val regex =
                """fill=\"#[a-zA-Z0-9]{6}\" data-count=\"\d+\" data-date=\"([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))\"""".toRegex()
            val element = doc.select("g rect")
            val commits = ArrayList<Commits>()
            for (e: Element in element) {
                val matchResult = regex.find(e.toString())?.value?.replace("\"", "")
                val split: List<String> = matchResult?.split(" ")!!
                commits.add(
                    Commits(
                        split[2].split("=")[1],
                        split[1].split("=")[1].toInt(),
                        split[0].split("=")[1]
                    )
                )
            }
            _commits.postValue(commits)
            setCommits(commits)
        }
    }
}
