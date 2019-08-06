package com.seok.gitfordeveloper.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
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