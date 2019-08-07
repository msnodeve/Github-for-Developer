package com.seok.gitfordeveloper.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.seok.gitfordeveloper.room.model.Commits

@Dao
interface CommitsDao {
    @Query("SELECT * FROM commits")
    fun getAll(): List<Commits>

    @Query("SELECT * FROM commits WHERE date=:date")
    fun getTodayCommit(date :String): Commits

    @Query("SELECT * FROM commits WHERE commits = (SELECT MAX(commits) as commits FROM commits)")
    fun getMaxCommit() : Commits

    /* import android.arch.persistence.room.OnConflictStrategy.REPLACE */
    @Insert(onConflict = REPLACE)
    fun insert(commit: Commits)

    @Query("DELETE from commits")
    fun deleteAll()
}