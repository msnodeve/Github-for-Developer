package com.seok.gitfordeveloper.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seok.gitfordeveloper.database.Commits
import com.seok.gitfordeveloper.database.CommitsDatabase
import com.seok.gitfordeveloper.retrofit.domain.User
import com.seok.gitfordeveloper.retrofit.response.UserRepository
import com.seok.gitfordeveloper.utils.ValidationCheck
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.lang.NullPointerException

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)
    private val validationCheck = ValidationCheck(application)
    private val commitsDatabase = CommitsDatabase.getInstance(application)
    private val _commit = MutableLiveData<Commits>()
    private val _commits = MutableLiveData<List<Commits>>()
    private val _existCommit = MutableLiveData<Boolean>()

    val commit: LiveData<Commits>
        get() = _commit
    val commits: LiveData<List<Commits>>
        get() = _commits
    val existCommit: LiveData<Boolean>
        get() = _existCommit

    fun githubUserApi(token: String): LiveData<User> {
        return userRepository.getUserMutableData(token)
    }

    fun fetchCommit(commit: Commits) {
        val commits = commit
        _commit.value = commits
    }

    fun getAllCommits() {
        doAsync {
            _commits.postValue(commitsDatabase.commitsDatabaseDao().getAllCommits())
        }
    }

    fun setCommit(commit: Commits) {
        doAsync {
            commitsDatabase.commitsDatabaseDao().insert(commit)
            getAllCommits()
        }
    }

    fun setCommits(commits: ArrayList<Commits>) {
        doAsync {
            for (commit in commits) {
                commitsDatabase.commitsDatabaseDao().insert(commit)
            }
            getAllCommits()
        }
    }

    fun checkCommit() {
        GlobalScope.launch {
            val query = async(Dispatchers.IO) {
                val today = validationCheck.existTodayCommit()
                try {
                    val commit = commitsDatabase.commitsDatabaseDao().getCommits(today).dataDate
                }catch (e : NullPointerException){
                    Log.e(this.javaClass.simpleName, e.message.toString())
                    _existCommit.postValue(true)
                }
            }
            query.await()
        }
    }
    fun getCommits(url : String){
        Log.d("testtest", url)
        doAsync {
            val doc = Jsoup.connect(url).get()
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