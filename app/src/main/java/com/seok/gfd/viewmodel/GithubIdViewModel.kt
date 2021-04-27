package com.seok.gfd.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seok.gfd.room.AppDatabase
import com.seok.gfd.room.entity.GithubId
import kotlinx.coroutines.runBlocking

class GithubIdViewModel(val context: Application) : AndroidViewModel(context) {
    private val TAG = this.javaClass.toString()
    private val database = AppDatabase.getInstance(context)

    private val _githubIds = MutableLiveData<List<GithubId>>()

    val githubIds: LiveData<List<GithubId>>
        get() = _githubIds

    fun insertGithubId(githubId: GithubId) {
        runBlocking {
            if (database.githubIdDao().selectById(githubId.githubId.toString()) == 0) {
                database.githubIdDao().insert(githubId)
            }
        }
    }

    fun getGithubId(name: String) {
        runBlocking {
            if (name == "" || name.isEmpty()) {
                _githubIds.value = database.githubIdDao().selectAll()
            } else {
                _githubIds.value = database.githubIdDao().selectAll(name)
            }
        }
    }

    fun deleteGithubId(githubId: GithubId) {
        runBlocking {
            database.githubIdDao().delete(githubId)
        }
    }

    fun closeDatabase() {
        database.close()
    }
}