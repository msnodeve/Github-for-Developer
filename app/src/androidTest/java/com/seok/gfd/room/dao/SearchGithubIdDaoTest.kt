package com.seok.gfd.room.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.seok.gfd.room.AppDatabase
import com.seok.gfd.room.entity.SearchGithubId
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchGithubIdDaoTest {
    private lateinit var db : AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.seok.gfd", context.packageName)

        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = runBlocking {
        db.searchGithubIdDao().deleteAll()
        db.close()
    }

    @Test
    fun iWantToKnowTheDatabaseIsFind() = runBlocking{
        val searchGithubId = SearchGithubId(gid = 0, gidName = "github")

        db.searchGithubIdDao().insert(searchGithubId)
        var entity = db.searchGithubIdDao().selectAll("g")[0]
        assertHabitEquals(searchGithubId, entity)

        db.searchGithubIdDao().delete(entity)
        assertEquals(0, db.searchGithubIdDao().selectAll("g").size)
    }

    private fun assertHabitEquals(expected: SearchGithubId, actual: SearchGithubId) {
        // id 는 자동생성되므로, 검증을 위해서 id의 동일성은 무시하자.
        assertEquals(expected.copy(gid = 0), actual.copy(gid = 0))
    }

}