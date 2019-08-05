package com.seok.gitfordeveloper.room.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "commit")
class Commit(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo var date: String,
    @ColumnInfo var commit: Int
) {
    constructor() : this(0, Date(System.currentTimeMillis()).toString(), 0)
}

