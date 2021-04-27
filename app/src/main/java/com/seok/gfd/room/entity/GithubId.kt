package com.seok.gfd.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

private const val TABLE_NAME = "search_github_id"

@Entity(tableName = TABLE_NAME)
data class GithubId(
    @ColumnInfo(name = "github_id") val githubId: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo(name = "created", defaultValue = "CURRENT_TIMESTAMP")
    var created: Date? = Date()
}