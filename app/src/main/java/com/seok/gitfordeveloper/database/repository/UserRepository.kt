package com.seok.gitfordeveloper.database.repository

import androidx.lifecycle.LiveData
import com.seok.gitfordeveloper.database.model.User

interface UserRepository {
    fun getAllUsers() : LiveData<List<User>>
    fun getUserByID(userID:String): LiveData<User>
}

