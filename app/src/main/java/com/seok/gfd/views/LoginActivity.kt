package com.seok.gfd.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.seok.gfd.BuildConfig
import com.seok.gfd.R
import kotlinx.android.synthetic.main.activity_login.*
import java.net.HttpURLConnection


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.seok.gfd.retrofit.RetrofitClient
import com.seok.gfd.utils.AuthUserToken
import com.seok.gfd.utils.ProgressbarDialog
import com.seok.gfd.viewmodel.LoginViewModel
import org.jetbrains.anko.longToast
import retrofit2.Call
import retrofit2.Response

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var authToken: AuthUserToken
    private lateinit var progressbarDialog: ProgressbarDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        checkForSignIn()
        login_img_login.setOnClickListener {
            progressbarDialog.show()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authToken.buildHttpUrl(BuildConfig.GITHUB_CLIENT_ID)))
            startActivityForResult(intent, HttpURLConnection.HTTP_OK)
        }

        val userService = RetrofitClient.gUserService()
        val call = userService.getUsersCount(BuildConfig.BASIC_AUTH_KEY)
        call.enqueue(object : retrofit2.Callback<Int>{
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.d("testtest", t.message)
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if(response.isSuccessful){
                    val body = response.body()
                    login_tv_users_count.text = body.toString()
                }else{
                    Log.d("testest", "t")
                }
            }

        })
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        authToken = AuthUserToken(application)
        progressbarDialog = ProgressbarDialog(this)
    }

    private fun checkForSignIn() {
        progressbarDialog.show()
        val accessToken = authToken.getToken(BuildConfig.PREFERENCES_TOKEN_KEY)
        if (accessToken != getString(R.string.no_token)) {
            viewModel.githubUserApi(accessToken).observe(this, Observer { body ->
                if (body.code == HttpURLConnection.HTTP_OK) {
                    goToMainActivity()
                } else {
                    longToast(getString(R.string.fail_token))
                    progressbarDialog.hide()
                }
            })
        } else {
            longToast(getString(R.string.welcome_app))
            progressbarDialog.hide()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = intent!!.data
        val code = authToken.getCode(uri.toString())
        getAccessToken(code)
    }

    private fun getAccessToken(code:String){
        viewModel.getGithubCode(BuildConfig.GITHUB_CLIENT_ID, BuildConfig.GITHUB_CLIENT_SECRET,code)
            .observe(this, Observer { body ->
                if(body.code == HttpURLConnection.HTTP_OK) {
                    authToken.editToken(BuildConfig.PREFERENCES_TOKEN_KEY, body.access_token)
                    goToMainActivity()
                }else{
                    longToast(getString(R.string.invalid_token))
                    progressbarDialog.hide()
                }
            })
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        progressbarDialog.hide()
        finish()
    }
}
