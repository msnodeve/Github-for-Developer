package com.seok.gitfordeveloper.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.seok.gitfordeveloper.database.Commits
import com.seok.gitfordeveloper.database.CommitsDatabase
import com.seok.gitfordeveloper.database.CommitsDatabaseDao
import org.jetbrains.anko.doAsync

class CommitsRepository(application: Application) {
    private val commitsDatabaseDao : CommitsDatabaseDao
    private val listCommits : LiveData<List<Commits>>

    init{
        val commitsDatabase = CommitsDatabase.getInstance(application)
        commitsDatabaseDao = commitsDatabase.commitsDatabaseDao()
        listCommits = commitsDatabaseDao.getAllCommits()
    }

    fun getAllCommits() : LiveData<List<Commits>>{
        return listCommits
    }

    fun insert(commits: Commits){
        doAsync { commitsDatabaseDao.insert(commits) }
    }

    fun getCommit(dataDate : String) : LiveData<Commits>{
        return commitsDatabaseDao.getCommits(dataDate)
    }
}