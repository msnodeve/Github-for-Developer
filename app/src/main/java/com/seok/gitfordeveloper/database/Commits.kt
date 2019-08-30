package com.seok.gitfordeveloper.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commits")
data class Commits(
    @PrimaryKey
    @ColumnInfo(name = "data_date")
    val dataDate: String,

    @ColumnInfo(name = "data_count")
    val dataCount: Int,

    @ColumnInfo(name = "fill")
    val fill: String
)