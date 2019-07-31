package com.seok.gitfordeveloper

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import java.io.IOException
import java.lang.Exception


class LoginActivity : AppCompatActivity(){
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mAuthListener : FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // FirebaseAuth
        mAuth = FirebaseAuth.getInstance()
        // Firebase AuthStateListener
        mAuthListener = object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
                val user : FirebaseUser? = firebaseAuth.currentUser

                // User is signed in
                if (user != null){
                    Log.d("dLog-Login-onAuth", "User is signed in")
                    Log.d("dLog-Login-onAuth", user.photoUrl.toString())
                    Log.d("dLog-Login-onAuth", user.uid)
                    Log.d("dLog-Login-onAuth", user.email.toString())

                }
                else{
                    Log.d("dLog-Login-onAuth", "User is signed out")
                }
            }

        }
        login_github_btn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        var httpUrl :HttpUrl = HttpUrl.Builder()
            .scheme("https")
            .host("github.com")
            .addPathSegment("login")
            .addPathSegment("oauth")
            .addPathSegment("authorize")
            .addQueryParameter("client_id", getString(R.string.github_app_id))
            .addQueryParameter("scope", "user:email")
            .build()
        var intent : Intent = Intent(Intent.ACTION_VIEW, Uri.parse(httpUrl.toString()))
        startActivityForResult(intent, StatusCode.REQUEST_GITHUB_REDIRECT)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if(intent != null){
            var code : String = intent.getStringExtra("code")
            if(code != null){
                Log.d("dLog-Login-onNewIntent", code)
                sendPost(code)
            }else{
                Log.d("dLog-Login-onNewIntent", "get code err")
            }
        }
    }

    private fun sendPost(code: String) {
        Log.d("dLog-Login-sendPost", "in sendPost()")
        val okHttpClient : OkHttpClient = OkHttpClient()
        val form = FormBody.Builder()
            .add("client_id", getString(R.string.github_app_id))
            .add("client_secret", getString(R.string.github_app_secret))
            .add("code", code)
            .build()

        val requset = Request.Builder()
            .url("https://github.com/login/oauth/access_token")
            .post(form)
            .build()

        okHttpClient.newCall(requset).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("dLog-Login-sendPost", "Fail")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("dLog-Login-sendPost", "Success")
                // e.g. Response form : access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer
                val responseBody : String = response.body()!!.string()
                var splittedBody : List<String> = responseBody.split("=","&")
                if (splittedBody[0].equals("access_token")){
                    Log.d("dLog-Login-sendPost", splittedBody[1])
                    signInWithToken(splittedBody[1])
                }
            }
        })
    }
    private fun signInWithToken(token : String){
        var credential = GithubAuthProvider.getCredential(token)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful){
                    Log.d("dLog-Login-onComplete", "signInWithToken() - task is successful")
                }
            }
            .addOnFailureListener(this){task ->
                Log.d("dLog-Login-onFailure", "signInWithToken() - signInWithCredential - onFailure");
            }
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthListener)
    }
}