package com.seok.gitfordeveloper

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import android.util.Log
import com.seok.gitfordeveloper.room.dao.CommitsDao
import com.seok.gitfordeveloper.room.database.CommitsDB
import com.seok.gitfordeveloper.room.model.Commits
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception
import org.junit.After
import org.junit.Assert
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class CommitsDBTest {
    private lateinit var commitsDao: CommitsDao
    private lateinit var commitsDB: CommitsDB

    @Before
    @Throws(Exception::class)
    fun start() {
//        val context = InstrumentationRegistry.getTargetContext()
//        commitsDB = Room.inMemoryDatabaseBuilder(context, CommitsDB::class.java).build()
        commitsDao = commitsDB.commitDao()
    }

    @Test
    fun commitsTest() {
        // DB 데이터 삽입 및 확인
        var date = "2019-08-04"
        var originCommit = Commits(date, 1)
        commitsDao.insert(originCommit)
        var testCommit = commitsDao.getTodayCommit(date)
        Assert.assertNotNull(testCommit)
        Assert.assertEquals(testCommit.date, originCommit.date)
        Assert.assertEquals(testCommit.commits, originCommit.commits)
        date = "2019-08-05"
        originCommit = Commits(date, 4)
        commitsDao.insert(originCommit)
        testCommit = commitsDao.getTodayCommit(date)
        Assert.assertNotNull(testCommit)
        Assert.assertEquals(testCommit.date, originCommit.date)
        Assert.assertEquals(testCommit.commits, originCommit.commits)

        // 데이터 개수 확인
        var commits = commitsDB.commitDao().getAll()
        Assert.assertNotNull(commits)
        Assert.assertEquals(commits.size, 2)

        // 최대값 가져오기
        val maxCommit = commitsDB.commitDao().getMaxCommit()
        Assert.assertNotNull(maxCommit)
        Assert.assertEquals(maxCommit.date, date)
        Assert.assertEquals(maxCommit.commits, 4)

        // 삭제하기
        commitsDB.commitDao().deleteAll()
        commits = commitsDB.commitDao().getAll()
        Assert.assertEquals(commits.size, 0)
    }

    @After
    @Throws(IOException::class)
    fun end() {
        Log.i("dbtest", "테스트 종료")
        commitsDB.close()
    }

}