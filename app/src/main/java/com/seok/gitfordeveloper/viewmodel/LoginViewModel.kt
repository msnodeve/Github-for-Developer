package com.seok.gitfordeveloper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.seok.gitfordeveloper.retrofit.domain.Token
import com.seok.gitfordeveloper.retrofit.domain.User
import com.seok.gitfordeveloper.retrofit.response.UserRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)

    fun githubUserApi(token: String): LiveData<User> {
        return userRepository.getUserMutableData(token)
    }

    fun getGithubCode(clientId: String, clientSecret: String, code: String): LiveData<Token> {
        return userRepository.getCodeMutableData(clientId, clientSecret, code)
    }
}