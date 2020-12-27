package com.seok.gfd.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seok.gfd.room.dao.SearchGithubIdDao
import com.seok.gfd.room.entity.SearchGithubId

@Database(entities = [SearchGithubId::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun searchGithubIdDao(): SearchGithubIdDao
}