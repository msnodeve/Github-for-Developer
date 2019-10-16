package com.seok.gitfordeveloper.database

import androidx.room.*

@Dao
interface CommitsDatabaseDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(commits : Commits)

    @Update
    fun update(commits: Commits)

    @Query("SELECT * FROM commits ORDER BY data_count DESC LIMIT 1")
    fun maxCommit() : Commits

    @Query("SELECT * FROM commits")
    fun getAllCommits() : List<Commits>

    @Query("SELECT * FROM commits WHERE data_date = :dataDate")
    fun getCommits(dataDate: String) : Commits

    @Query("SELECT * FROM commits ORDER BY data_date DESC")
    fun getYearCommits() : List<Commits>

}