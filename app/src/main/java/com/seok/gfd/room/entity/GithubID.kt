package com.seok.gfd.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GithubID(
    @PrimaryKey val gid : Int,
    @ColumnInfo(name = "gid_name") val gidName : String?
)
