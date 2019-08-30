package com.seok.gitfordeveloper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Commits::class], version = 1, exportSchema = false)
abstract class CommitsDatabase : RoomDatabase(){
    abstract fun commitsDatabaseDao(): CommitsDatabaseDao
    companion object{
        @Volatile
        private var INSTANCE: CommitsDatabase? = null

        fun getInstance(context: Context): CommitsDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CommitsDatabase::class.java,
                        "commits_db")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}