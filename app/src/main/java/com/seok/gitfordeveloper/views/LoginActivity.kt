package com.seok.gitfordeveloper.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.seok.gitfordeveloper.AuthGithub
import com.seok.gitfordeveloper.BuildConfig
import com.seok.gitfordeveloper.R
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import java.io.IOException
import java.net.HttpURLConnection


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.seok.gitfordeveloper.retrofit.service.UserService
import com.seok.gitfordeveloper.viewmodel.LoginViewModel

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel : LoginViewModel
    private lateinit var githubUserService: UserService
    private  lateinit var authGithub: AuthGithub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        viewModel.getGithubApi("cd967ce5f09cdded2c3a5968552d3de7ffb2ad7c").observe(this, Observer {test->
            textView8.text = test.avatar_url
        })

//        init()
//        setUp()

        login_img_login.setOnClickListener{
            requestIntent()
        }
    }
    private fun init(){
        authGithub = AuthGithub()
    }

    private fun setUp(){
        val accessToken = getAccessToken()
        if(accessToken != getString(R.string.no_token)){
            val validApiCall = githubUserService.getGithubApi("Bearer $accessToken")
//            validApiCall.enqueue(object : retrofit2.Callback<User>{
//                override fun onResponse(call: retrofit2.Call<User>, response: retrofit2.Response<User>) {
//                    if(response.isSuccessful){
//                        if(response.body()==null){
//                            // 토큰은 저장되어있으나 맞지 않는 토큰 일경우 로그인 버튼 자동 클릭
//                            requestIntent()
//                        }else{
//                            goToMainActivity()
//                        }
//                    }
//                }
//                override fun onFailure(call: retrofit2.Call<User>, t: Throwable) {
//                    Log.e(this.javaClass.simpleName, t.message.toString())
//                }
//            })

        }
    }
    private fun requestIntent(){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(
            authGithub.buildHttpUrl(BuildConfig.GITHUB_CLIENT_ID)))
        startActivityForResult(intent, HttpURLConnection.HTTP_OK)
    }

    private fun getAccessToken() : String? {
        return if(existAccessToken()){
            val pref = getSharedPreferences(BuildConfig.PREFERENCES_FILE, MODE_PRIVATE)
            pref.getString(BuildConfig.PREFERENCES_TOKEN_KEY,null)
        } else{
            getString(R.string.no_token)
        }
    }

    private fun existAccessToken() : Boolean{
        val pref = getSharedPreferences(BuildConfig.PREFERENCES_FILE, MODE_PRIVATE)
        return pref.contains(BuildConfig.PREFERENCES_TOKEN_KEY)
    }

    override fun onNewIntent(intent: Intent?){
        super.onNewIntent(intent)
        val uri = intent!!.data
        val authGithub = AuthGithub()
        val code = authGithub.getCode(uri.toString())
        sendPost(code)
    }

    private fun sendPost(code : String){
        val request = makeRequest(code)
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val accessToken = authGithub.getToken(response.body()!!.string())
                val pref = getSharedPreferences(BuildConfig.PREFERENCES_FILE, MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString(BuildConfig.PREFERENCES_TOKEN_KEY, accessToken)
                editor.commit()
                goToMainActivity()
            }
            override fun onFailure(call: Call, e: IOException) {
                Log.d(LoginActivity::getLocalClassName.toString(), e.toString())
            }
        })

    }
    private fun makeRequest(code : String) :Request{
        val form = FormBody.Builder()
            .add("client_id", BuildConfig.GITHUB_CLIENT_ID)
            .add("client_secret", BuildConfig.GITHUB_CLIENT_SECRET)
            .add("code", code)
            .build()
        return Request.Builder()
            .url(getString(R.string.github_oauth_url))
            .post(form)
            .build()
    }
    private fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
