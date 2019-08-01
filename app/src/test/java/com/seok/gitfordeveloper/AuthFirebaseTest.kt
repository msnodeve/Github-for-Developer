package com.seok.gitfordeveloper

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.seok.gitfordeveloper.firebase.AuthFirebase
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.OngoingStubbing
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AuthFirebaseTest {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var authFirebase : AuthFirebase

    @Before
    fun setup(){

    }

    @Test
    fun getUserTest(){

    }
}
