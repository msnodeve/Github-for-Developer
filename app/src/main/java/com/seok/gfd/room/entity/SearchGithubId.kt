package com.seok.gfd.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

private const val TABLE_NAME = "search_github_ids"

@Entity(tableName = TABLE_NAME)
data class SearchGithubId(
    @ColumnInfo(name = "gid_name") val gidName: String?
) {
    @PrimaryKey(autoGenerate = true)
    var gid: Int = 0
    @ColumnInfo(name = "created", defaultValue = "CURRENT_TIMESTAMP")
    var created: Date? = Date()
}