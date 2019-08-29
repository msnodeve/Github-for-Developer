package com.seok.gitfordeveloper.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.seok.gitfordeveloper.database.model.User

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