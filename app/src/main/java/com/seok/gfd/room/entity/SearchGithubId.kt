package com.seok.gfd.room.entity

import androidx.room.*
import java.util.*

private const val TABLE_NAME = "search_github_ids"

@Entity(tableName = TABLE_NAME)
data class SearchGithubId(
    @PrimaryKey(autoGenerate = true) var gid: Int,
    @ColumnInfo(name = "gid_name") val gidName: String?,
    @ColumnInfo(name = "created", defaultValue = "CURRENT_TIMESTAMP") val created: Date? = Date()
)

@Dao
interface SearchGithubIdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchGithubId: SearchGithubId) : Int

    @Delete
    suspend fun delete(searchGithubId: SearchGithubId)

    @Query("SELECT * FROM $TABLE_NAME WHERE gid_name = :gidName")
    suspend fun selectAll(gidName : String) : List<SearchGithubId>
}

@Database(entities = [SearchGithubId::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun searchGithubIdDao(): SearchGithubIdDao
}