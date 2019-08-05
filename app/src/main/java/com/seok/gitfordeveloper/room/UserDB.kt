package com.seok.gitfordeveloper.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [User::class], version = 2)
abstract class UserDB: RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        private var INSTANCE: UserDB? = null
        fun getInstance(context: Context): UserDB? {
            if (INSTANCE == null) {
                synchronized(UserDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserDB::class.java, "user.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}