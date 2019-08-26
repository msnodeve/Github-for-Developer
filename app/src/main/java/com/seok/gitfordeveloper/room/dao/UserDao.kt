package com.seok.gitfordeveloper.room.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.seok.gitfordeveloper.room.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE user_id = :userID")
    fun getUser(userID: String): LiveData<User>

    @Insert(onConflict = REPLACE)
    fun insert(user: User)

    @Query("DELETE from user WHERE user_id = :userID")
    fun deleteById(userID: String)
}