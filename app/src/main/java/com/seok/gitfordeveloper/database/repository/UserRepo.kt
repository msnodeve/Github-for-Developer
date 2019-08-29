package com.seok.gitfordeveloper.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.seok.gitfordeveloper.database.dao.UserDao
import com.seok.gitfordeveloper.database.database.UserDB
import com.seok.gitfordeveloper.database.model.User

class UserRepo(application: Application) : ViewModel(){
    private val userDao : UserDao by lazy {
        val db = UserDB.getInstance(application)!!
        db.userDao()
    }

    private val users: LiveData<List<User>> by lazy {
        userDao.getAllUsers()
    }

    fun getAllUsers() : LiveData<List<User>>{
        return users
    }

    fun getUserByID(userID:String): LiveData<User>{
        return userDao.getUser(userID)
    }

//    fun insert(user:User) : Observer<User>{
//        return Observer()
//    }
}