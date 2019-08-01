package com.seok.gitfordeveloper.firebase

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GithubAuthProvider

class AuthFirebase {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    init {// AuthFirebase 생성시 초기화 할때 Firebase에 등록된 Userinfo 가져옴
        Log.d("dLog-AFB-init", "init start")
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(auth: FirebaseAuth) {
                val user = getUser(auth)
                setUser(user!!)
            }
        }
    }
    constructor(){
        Log.d("dLog-AFB-init", "constructor start")
        mAuth.addAuthStateListener(mAuthListener)
    }

    public fun getUser(auth: FirebaseAuth): FirebaseUser? {
        return auth.currentUser!!
    }

    private fun setUser(user : FirebaseUser){
        // 정보가 있다면 객체화 시킬 것
        if(user != null){
            Log.d("dLog-AFB-setUser", "User is not empty")
            Log.d("dLog-AFB-setUser", user.photoUrl.toString())
            Log.d("dLog-AFB-setUser", user.displayName)
            Log.d("dLog-AFB-setUser", user.email.toString())
        }else{
            Log.d("dLog-AFB-setUser", "User is empty")
        }
    }

    public fun signInWithToken(token : String, activity: Activity){
        var credential = GithubAuthProvider.getCredential(token)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if(task.isSuccessful) {
                    // Firebase와 통신 성공
                }
            }
            .addOnFailureListener(activity){
                // Firebase와 통신 실패
            }

    }
}