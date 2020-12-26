package com.seok.gfd.room.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.seok.gfd.room.AppDatabase
import com.seok.gfd.room.entity.SearchGithubId
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class SearchGithubIdDaoTest {
    private lateinit var githubIdDao: SearchGithubIdDao
    private lateinit var db : AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        githubIdDao = db.searchGithubDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun write(){
        val searchGithubId : SearchGithubId = SearchGithubId(1, "2", Date())
        githubIdDao.insertSearchGithubId(searchGithubId)
        val byName = githubIdDao.selectGithubIds("2")
        println(byName)
    }

}