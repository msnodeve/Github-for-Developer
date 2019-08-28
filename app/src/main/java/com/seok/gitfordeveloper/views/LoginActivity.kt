package com.seok.gitfordeveloper.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.seok.gitfordeveloper.utils.AuthGithub
import com.seok.gitfordeveloper.BuildConfig
import com.seok.gitfordeveloper.R
import kotlinx.android.synthetic.main.activity_login.*
import java.net.HttpURLConnection


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.seok.gitfordeveloper.utils.SharedPreference
import com.seok.gitfordeveloper.viewmodel.LoginViewModel
import org.jetbrains.anko.longToast

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedReference: SharedPreference
    private lateinit var authGithub: AuthGithub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        checkForSignIn()

        login_img_login.setOnClickListener {
            requestIntent()
        }
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        sharedReference = SharedPreference(application)
        authGithub = AuthGithub()
    }
    private fun checkForSignIn() {
        val accessToken = getToken()
        if (accessToken != getString(R.string.no_token)) {
            viewModel.githubUserApi(accessToken).observe(this, Observer { body ->
                if (body.code == HttpURLConnection.HTTP_OK) {
                    goToMainActivity()
                } else {
                    longToast(getString(R.string.fail_token))
                }
            })
        } else {
            longToast(getString(R.string.welcome_app))
        }
    }

    private fun getToken(): String {
        return if (sharedReference.checkForToken(BuildConfig.PREFERENCES_TOKEN_KEY)) {
            sharedReference.getToken(BuildConfig.PREFERENCES_TOKEN_KEY)
        } else {
            getString(R.string.no_token)
        }
    }

    private fun requestIntent() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authGithub.buildHttpUrl(BuildConfig.GITHUB_CLIENT_ID)))
        startActivityForResult(intent, HttpURLConnection.HTTP_OK)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = intent!!.data
        val authGithub = AuthGithub()
        val code = authGithub.getCode(uri.toString())
        getAccessToken(code)
    }
    private fun getAccessToken(code:String){
        viewModel.getGithubCode(BuildConfig.GITHUB_CLIENT_ID, BuildConfig.GITHUB_CLIENT_SECRET,code)
            .observe(this, Observer { body ->
                if(body.code == HttpURLConnection.HTTP_OK) {
                    sharedReference.editToken(BuildConfig.PREFERENCES_TOKEN_KEY, body.access_token)
                    goToMainActivity()
                }else{
                    longToast(getString(R.string.invalid_token))
                }
            })
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
