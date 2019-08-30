package com.seok.gitfordeveloper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seok.gitfordeveloper.database.Commits
import com.seok.gitfordeveloper.retrofit.domain.User
import com.seok.gitfordeveloper.retrofit.response.UserRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)

    private val _commit = MutableLiveData<Commits>()

    val commit: LiveData<Commits>
        get() = _commit

    fun fetchCommit(commit: Commits) {
        val commits = commit

        _commit.value = commits
    }

    fun githubUserApi(token: String): LiveData<User> {
        return userRepository.getUserMutableData(token)
    }

}