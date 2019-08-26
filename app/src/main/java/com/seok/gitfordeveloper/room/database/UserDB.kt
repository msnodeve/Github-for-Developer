package com.seok.gitfordeveloper.room.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.seok.gitfordeveloper.room.dao.UserDao
import com.seok.gitfordeveloper.room.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDB : RoomDatabase(){
    abstract fun userDao() : UserDao

    companion object{
        private var INSTANCE : UserDB? = null
        fun getInstance(context: Context) : UserDB{
            return INSTANCE ?: synchronized(UserDB::class) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                    UserDB::class.java, "user.db").build().also { INSTANCE = it }
            }

        }

        fun destoryInstance(){
            INSTANCE = null
        }
    }
}