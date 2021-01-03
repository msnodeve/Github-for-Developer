package com.seok.gfd.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seok.gfd.room.AppDatabase
import com.seok.gfd.room.entity.SearchGithubId
import kotlinx.coroutines.runBlocking

class GithubIdViewModel(val context: Application) : AndroidViewModel(context){
    private val TAG = this.javaClass.toString()
    private val database = AppDatabase.getInstance(context)

    private val _githubIds = MutableLiveData<List<SearchGithubId>>()

    val githubIds : LiveData<List<SearchGithubId>>
        get() = _githubIds

    fun insertGithubId(githubId: SearchGithubId){
        runBlocking {
            database.searchGithubIdDao().insert(githubId)
        }
    }

    fun getGithubId(name : String){
        runBlocking {
            if(name == "" || name.isEmpty()) {
                _githubIds.value = database.searchGithubIdDao().selectAll()
            }else{
                _githubIds.value = database.searchGithubIdDao().selectAll(name)
            }
        }
    }

    fun closeDatabase(){
        database.close()
    }
}