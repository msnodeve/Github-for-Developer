package com.seok.gfd.room.dao

import androidx.room.*
import com.seok.gfd.room.entity.SearchGithubId

private const val TABLE_NAME = "search_github_ids"

@Dao
interface SearchGithubIdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchGithubId: SearchGithubId): Long

    @Delete
    suspend fun delete(searchGithubId: SearchGithubId)

    @Query("SELECT * FROM $TABLE_NAME WHERE gid_name LIKE '%' || :gidName || '%' ORDER BY created DESC")
    suspend fun selectAll(gidName: String): List<SearchGithubId>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY created DESC")
    suspend fun selectAll(): List<SearchGithubId>

    @Query("delete from $TABLE_NAME")
    suspend fun deleteAll()
}