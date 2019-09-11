package com.seok.gitfordeveloper.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seok.gitfordeveloper.database.CommitsDatabase
import com.seok.gitfordeveloper.retrofit.domain.User
import com.seok.gitfordeveloper.retrofit.response.UserRepository
import com.seok.gitfordeveloper.utils.ValidationCheck
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.async

class MainFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val userRepository = UserRepository(application)
    private val validationCheck = ValidationCheck(application)
    private val commitsDatabase = CommitsDatabase.getInstance(application)


    private val _existCommit = MutableLiveData<Boolean>()
    val existCommit: LiveData<Boolean>
        get() = _existCommit


    fun githubUserApi(token: String) : LiveData<User>{
        return userRepository.getUserMutableData(token)
    }

    fun checkCommit(){
        GlobalScope.launch {
            val query = async(Dispatchers.IO){
                val today = validationCheck.existTodayCommit()
                try{
                    commitsDatabase.commitsDatabaseDao().getCommits(today).dataCount
                }catch (e : NullPointerException){
                    Log.e(this.javaClass.simpleName, e.message.toString())
                    _existCommit.postValue(true)
                }
            }
            query.await()
        }
    }
}