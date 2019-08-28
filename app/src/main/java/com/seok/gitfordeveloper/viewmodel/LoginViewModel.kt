package com.seok.gitfordeveloper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.seok.gitfordeveloper.retrofit.RetrofitClient
import com.seok.gitfordeveloper.retrofit.domain.User
import com.seok.gitfordeveloper.retrofit.response.UserRepository
import com.seok.gitfordeveloper.retrofit.service.UserService

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)

    public fun getGithubApi(token :String) : LiveData<User>{
        return userRepository.getMutableLiveData(token)
    }
}