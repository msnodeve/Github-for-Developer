package com.seok.gitfordeveloper.room.repository

import androidx.lifecycle.LiveData
import com.seok.gitfordeveloper.room.model.User

interface UserRepository {
    fun getAllUsers() : LiveData<List<User>>
    fun getUserByID(userID:String): LiveData<User>
}

