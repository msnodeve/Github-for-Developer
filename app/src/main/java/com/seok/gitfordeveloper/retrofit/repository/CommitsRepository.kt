package com.seok.gitfordeveloper.retrofit.repository

import android.app.Application
import com.seok.gitfordeveloper.database.Commits
import com.seok.gitfordeveloper.database.CommitsDatabase
import com.seok.gitfordeveloper.database.CommitsDatabaseDao
import org.jetbrains.anko.doAsync

class CommitsRepository(application: Application) {
    private val commitsDatabaseDao : CommitsDatabaseDao

    init{
        val commitsDatabase = CommitsDatabase.getInstance(application)
        commitsDatabaseDao = commitsDatabase.commitsDatabaseDao()
    }

    fun insert(commits: Commits){
        doAsync { commitsDatabaseDao.insert(commits) }
    }
}