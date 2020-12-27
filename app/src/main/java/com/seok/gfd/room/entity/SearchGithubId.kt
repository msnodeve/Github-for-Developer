package com.seok.gfd.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

private const val TABLE_NAME = "search_github_ids"

@Entity(tableName = TABLE_NAME)
data class SearchGithubId(
    @PrimaryKey(autoGenerate = true) var gid: Long,
    @ColumnInfo(name = "gid_name") val gidName: String?
//    @ColumnInfo(name = "created", defaultValue = "CURRENT_TIMESTAMP") val created: Date? = Date()
)