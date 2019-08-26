package com.seok.gitfordeveloper.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "commits")
class Commits(
    @PrimaryKey var date: String,
    @ColumnInfo var commits: Int
) {
    constructor() : this(Date(System.currentTimeMillis()).toString(), 0)
}

