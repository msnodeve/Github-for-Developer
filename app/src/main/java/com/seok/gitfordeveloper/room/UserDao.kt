package com.seok.gitfordeveloper.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.seok.gitfordeveloper.BuildConfig

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE mac=:mac")
    fun getUser(mac :String): User

    /* import android.arch.persistence.room.OnConflictStrategy.REPLACE */
    @Insert(onConflict = REPLACE)
    fun insert(user: User)

    @Query("DELETE from user")
    fun deleteAll()
}