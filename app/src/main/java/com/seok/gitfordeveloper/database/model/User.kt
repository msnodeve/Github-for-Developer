package com.seok.gitfordeveloper.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter


@Getter
@Setter
@NoArgsConstructor
@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    var userID: String,

    @ColumnInfo(name = "token")
    var token : String,

    @ColumnInfo(name = "session")
    var session : Boolean,

    @ColumnInfo(name = "create_date")
    @SerializedName(value = "create_date")
    var creationDate: String
)