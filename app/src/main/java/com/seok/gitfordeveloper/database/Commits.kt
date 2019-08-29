package com.seok.gitfordeveloper.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commits")
data class Commits(
    @PrimaryKey
    @ColumnInfo(name = "data_date")
    var dataDate: String = "",

    @ColumnInfo(name = "data_count")
    var dataCount: Int = 0,

    @ColumnInfo(name = "fill")
    var fill: String = ""
)