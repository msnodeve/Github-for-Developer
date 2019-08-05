package com.seok.gitfordeveloper.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.sql.Date

@Entity(tableName = "user")
class User(@PrimaryKey var mac: String,
           @ColumnInfo var token: String,
           @ColumnInfo(name = "create_date")
           @SerializedName(value = "create_date")
           var creationDate: String){
    constructor(): this("","",Date(System.currentTimeMillis()).toString())
}