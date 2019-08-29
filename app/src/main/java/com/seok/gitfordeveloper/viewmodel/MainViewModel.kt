package com.seok.gitfordeveloper.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seok.gitfordeveloper.retrofit.domain.User
import com.seok.gitfordeveloper.retrofit.response.UserRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)

    fun githubUserApi(token: String) : LiveData<User>{
        return userRepository.getUserMutableData(token)
    }
}