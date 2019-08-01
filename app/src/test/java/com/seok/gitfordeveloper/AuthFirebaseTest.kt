package com.seok.gitfordeveloper

import com.google.firebase.auth.FirebaseAuth
import com.seok.gitfordeveloper.firebase.AuthFirebase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class AuthFirebaseTest {
    private val authFirebase: AuthFirebase? = AuthFirebase()

    @Mock
    private lateinit var mAuth: FirebaseAuth

    @Before
    fun setup() {
        mAuth = FirebaseAuth.getInstance()
    }

    @Test
    fun getUserTest() {
        var user = authFirebase!!.getUser(mAuth)
    }
}