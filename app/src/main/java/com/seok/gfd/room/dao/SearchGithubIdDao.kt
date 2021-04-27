package com.seok.gfd.room.dao

import androidx.room.*
import com.seok.gfd.room.entity.GithubId

private const val TABLE_NAME = "search_github_id"

@Dao
interface GithubIdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(githubId: GithubId): Long

    @Delete
    suspend fun delete(githubId: GithubId)

    @Query("SELECT * FROM $TABLE_NAME WHERE github_id LIKE '%' || :githubId || '%' ORDER BY created DESC")
    suspend fun selectAll(githubId: String): List<GithubId>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY created DESC")
    suspend fun selectAll(): List<GithubId>

    @Query("SELECT COUNT(*) FROM $TABLE_NAME WHERE github_id = :githubId")
    suspend fun selectById(githubId: String): Int

    @Query("delete from $TABLE_NAME")
    suspend fun deleteAll()
}