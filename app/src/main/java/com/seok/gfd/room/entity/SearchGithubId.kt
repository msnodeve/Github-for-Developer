package com.seok.gfd.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "search_github_ids")
data class SearchGithubId(
    @PrimaryKey var gid: Int,
    @ColumnInfo(name = "gid_name") val gidName: String?,
    @ColumnInfo(name = "created", defaultValue = "CURRENT_TIMESTAMP") val created: Date?
)
