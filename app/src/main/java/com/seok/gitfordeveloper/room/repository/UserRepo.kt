package com.seok.gitfordeveloper.room.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.database.Observable
import com.seok.gitfordeveloper.room.dao.UserDao
import com.seok.gitfordeveloper.room.database.UserDB
import com.seok.gitfordeveloper.room.model.User

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