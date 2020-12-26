package com.seok.gfd.room.dao

import androidx.room.*
import com.seok.gfd.room.entity.SearchGithubId
import java.util.*

@Dao
abstract class SearchGithubIdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSearchGithubId(searchGithubId: SearchGithubId)

    @Delete
    abstract fun deleteSearchGithubId(searchGithubId: SearchGithubId)

    @Query("SELECT * FROM search_github_ids WHERE gid_name = :gidName")
    abstract fun selectGithubIds(gidName : String) : List<SearchGithubId>
}