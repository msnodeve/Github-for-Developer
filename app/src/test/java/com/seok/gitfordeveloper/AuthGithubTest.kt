package com.seok.gitfordeveloper

import android.content.Context
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import com.seok.gitfordeveloper.github.AuthGithub
import okhttp3.FormBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AuthGithubTest {

    @Mock
    private lateinit var context: Context
    private lateinit var authGithub: AuthGithub

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(context.applicationContext).thenReturn(context)
        authGithub = AuthGithub(context)
    }

    @Test
    fun redirectURLActivityTest() {
        val intent = authGithub.redirectURLActivity()
        assert(intent != null)
    }

    @Test
    fun sendPostTest(){
        val code = "Token Code"
//        val result = authGithub.sendPost(code)
//        val form = FormBody.Builder()
//            .add("client_id", this.context.getString(R.string.github_app_id))
//            .add("client_secret", this.context.getString(R.string.github_app_secret))
//            .add("code", "Token Code")
//            .build()

//        Assert.assertEquals(result, form)
    }
}