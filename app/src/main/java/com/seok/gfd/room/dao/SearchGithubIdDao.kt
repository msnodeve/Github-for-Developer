package com.seok.gfd.room.dao

import androidx.room.*
import com.seok.gfd.room.entity.SearchGithubId

@Dao
interface SearchGithubIdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchGithubId: SearchGithubId) : Int

    @Delete
    suspend fun delete(searchGithubId: SearchGithubId)

//    @Query("SELECT * FROM $TABLE_NAME WHERE gid_name = :gidName")
    abstract fun selectAll(gidName : String) : List<SearchGithubId>
}