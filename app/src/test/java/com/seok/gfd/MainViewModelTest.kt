package com.seok.gfd

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.seok.gfd.database.Commits
import com.seok.gfd.viewmodel.MainViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class MainViewModelTest {
//    @get:Rule
//    val rule = InstantTaskExecutorRule()
//    private lateinit var viewModel: MainViewModel
//    private val observer: Observer<Commits> = mock()
//    private val observers: Observer<List<Commits>> = mock()
//    private val application : Application = mock(Application::class.java)


    @Before
    fun before() {
//        MockitoAnnotations.initMocks(this)
//        viewModel = MainViewModel(application)
//        viewModel.commit.observeForever(observer)
//        viewModel.commits.observeForever(observers)
    }

    @Test
    fun useAppContext() {
//        val expectedUser = Commits("test",1,"test")
//
//        viewModel.fetchCommit(expectedUser)
//
//        val captor = ArgumentCaptor.forClass(Commits::class.java)
//        captor.run {
//            Mockito.verify(observer, Mockito.times(1)).onChanged(capture())
//            Assert.assertEquals(expectedUser, value)
//        }
    }

    @Test
    fun commitsTest(){
//        val commit = Commits("2018-01-01",5,"#ef22fe")
//        viewModel.setCommit(commit)
//        val captor = ArgumentCaptor.forClass(Commits::class.java)
//        captor.run {
//            Mockito.verify(observers, Mockito.times(1)).onChanged(listOf(capture()))
//            Assert.assertEquals(commit, value)
//        }
    }
}
