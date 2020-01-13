package com.seok.gfd.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seok.gfd.retrofit.domain.Token
import com.seok.gfd.retrofit.domain.User
import com.seok.gfd.retrofit.repository.UserRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)
    private val _usersCount = MutableLiveData<Int>()

    val userCount: LiveData<Int>
        get() = _usersCount





    fun githubUserApi(token: String): LiveData<User> {
        return userRepository.getUserMutableData(token)
    }

    fun getGithubCode(clientId: String, clientSecret: String, code: String): LiveData<Token> {
        return userRepository.getCodeMutableData(clientId, clientSecret, code)
    }
}
