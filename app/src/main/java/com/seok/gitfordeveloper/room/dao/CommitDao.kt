package com.seok.gitfordeveloper.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.seok.gitfordeveloper.room.model.Commit
import com.seok.gitfordeveloper.room.model.User

@Dao
interface CommitDao {
    @Query("SELECT * FROM 'commit'")
    fun getAll(): List<Commit>

    @Query("SELECT * FROM 'commit' WHERE date=:date")
    fun getTodayCommit(date :String): Commit

    /* import android.arch.persistence.room.OnConflictStrategy.REPLACE */
    @Insert(onConflict = REPLACE)
    fun insert(commit: Commit)

    @Query("DELETE from 'commit'")
    fun deleteAll()
}