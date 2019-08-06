package com.seok.gitfordeveloper.room.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.seok.gitfordeveloper.room.dao.CommitDao
import com.seok.gitfordeveloper.room.dao.UserDao
import com.seok.gitfordeveloper.room.model.Commit
import com.seok.gitfordeveloper.room.model.User

@Database(entities = [Commit::class], version = 2)
abstract class CommitDB: RoomDatabase() {
    abstract fun commitDao(): CommitDao
    companion object {
        private var INSTANCE: CommitDB? = null
        fun getInstance(context: Context): CommitDB? {
            if (INSTANCE == null) {
                synchronized(CommitDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        CommitDB::class.java, "commit.db")
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