package com.seok.gitfordeveloper.room.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "commits")
class Commits(
    @PrimaryKey var date: String,
    @ColumnInfo var commits: Int
) {
    constructor() : this(Date(System.currentTimeMillis()).toString(), 0)
}

