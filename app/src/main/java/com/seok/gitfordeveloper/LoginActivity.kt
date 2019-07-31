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


class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // FirebaseAuth
        mAuth = FirebaseAuth.getInstance()
        // Firebase AuthStateListener 나중에 구글에 올릴 코드 자동로그인
//        mAuthListener = object : FirebaseAuth.AuthStateListener{
//            override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
//                val user : FirebaseUser? = firebaseAuth.currentUser
//                // User is signed in
//                if (user != null){
//                    Log.d("dLog-Login-onAuth", "User is signed in")
//                    Log.d("dLog-Login-onAuth", user.photoUrl.toString())
//                    Log.d("dLog-Login-onAuth", user.displayName)
//                    Log.d("dLog-Login-onAuth", user.email.toString())
//                    var intent = Intent(this@LoginActivity, MainActivity::class.java)
//                    startActivity(intent)
//                }
//                else{
//                    Log.d("dLog-Login-onAuth", "User is signed out")
//                }
//            }
//
//        }
        login_github_btn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        // 깃허브 로그인 시도, redirect_url로 code 토큰을 받아옴
        var httpUrl: HttpUrl = HttpUrl.Builder()
            .scheme("https")
            .host("github.com")
            .addPathSegment("login")
            .addPathSegment("oauth")
            .addPathSegment("authorize")
            .addQueryParameter("client_id", getString(R.string.github_app_id))
            .addQueryParameter("scope", "user:email")
            .build()
        var intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(httpUrl.toString()))
        startActivityForResult(intent, StatusCode.REQUEST_GITHUB_REDIRECT)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 발급 받은 code 토큰으로 엑세스 토큰을 받아올 것
        if (intent != null) {
            var code: String = intent.getStringExtra("code")
            if (code != null) {
                Log.d("dLog-Login-onNewIntent", code)
                sendPost(code)
            } else {
                Log.d("dLog-Login-onNewIntent", "get code err")
            }
        }
    }

    private fun sendPost(code: String) {
        Log.d("dLog-Login-sendPost", "in sendPost()")
        // 엑세스 토큰을 받아오기 위한 작업
        val okHttpClient: OkHttpClient = OkHttpClient()
        val form = FormBody.Builder()
            .add("client_id", getString(R.string.github_app_id))
            .add("client_secret", getString(R.string.github_app_secret))
            .add("code", code)
            .build()

        val requset = Request.Builder()
            .url("https://github.com/login/oauth/access_token")
            .post(form)
            .build()

        okHttpClient.newCall(requset).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("dLog-Login-sendPost", "Fail")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("dLog-Login-sendPost", "Success")
                // 아래와 같은 엑세스 토큰을 얻음 따라서 해당 토큰으로 깃허브 API를 받아올 수 있음.
                // e.g. Response form : access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer
                val responseBody: String = response.body()!!.string()
                var splittedBody: List<String> = responseBody.split("=", "&")
                if (splittedBody[0].equals("access_token")) {
                    Log.d("dLog-Login-sendPost", splittedBody[1])
                    signInWithToken(splittedBody[1])
                }
            }
        })
    }

    private fun signInWithToken(token: String) {
        var credential = GithubAuthProvider.getCredential(token)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //나중에 다음 엑티비티로 넘어가기 전에 프로그래스 바를 하나 둬야할 것 같음
                    Log.d("dLog-Login-onComplete", "signInWithToken() - task is successful")
                    UserInfo.access_token = token
                    var intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            .addOnFailureListener(this) { task ->
                Log.d("dLog-Login-onFailure", "signInWithToken() - signInWithCredential - onFailure");
            }
    }

    override fun onStart() {
        super.onStart()
//        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
//        mAuth.removeAuthStateListener(mAuthListener)
    }
}