package com.seok.gitfordeveloper.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "userinfo",
    foreignKeys = [ForeignKey(entity = User::class,
        parentColumns = arrayOf("user_id"),
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