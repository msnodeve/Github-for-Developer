package com.seok.gitfordeveloper.room.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "userinfo",
    foreignKeys = [ForeignKey(entity = User::class,
        parentColumns = arrayOf("mac"),
        childColumns = arrayOf("id"),
        onDelete = CASCADE)]
)
class UserInfo(
    @PrimaryKey var id: String,
    @ColumnInfo var login: String,
    @ColumnInfo var html_url: String,
    @ColumnInfo var avatar_url : String
){
    constructor() : this("", "", "", "")
}