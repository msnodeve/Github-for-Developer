package com.seok.gitfordeveloper.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.seok.gitfordeveloper.room.model.User
import com.seok.gitfordeveloper.room.model.UserInfo

@Dao
interface UserInfoDao {
    @Query("SELECT * FROM userinfo")
    fun getAll(): List<UserInfo>

    @Query("SELECT * FROM userinfo WHERE id=:mac")
    fun getUserInfo(mac :String): UserInfo

    /* import android.arch.persistence.room.OnConflictStrategy.REPLACE */
    @Insert(onConflict = REPLACE)
    fun insert(userinfo: UserInfo)


    @Query("DELETE from userinfo")
    fun deleteAll()
}