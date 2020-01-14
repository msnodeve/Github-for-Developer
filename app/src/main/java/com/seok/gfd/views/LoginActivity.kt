package com.seok.gfd.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seok.gfd.BuildConfig
import com.seok.gfd.R
import kotlinx.android.synthetic.main.activity_login.*
import java.net.HttpURLConnection


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.seok.gfd.utils.AuthUserToken
import com.seok.gfd.utils.ProgressbarDialog
import com.seok.gfd.utils.SharedPreference
import com.seok.gfd.viewmodel.UserViewModel
import org.jetbrains.anko.longToast

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel : UserViewModel
    private lateinit var sharedPreference: SharedPreference
    private lateinit var progressbarDialog: ProgressbarDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        initViewModelFun()


        // 로그인 버튼 눌렀을 경우 Github login 창으로 넘김
        login_img_login.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.GITHUB_OAUTH_URL + BuildConfig.GITHUB_CLIENT_ID))
            // onNewIntent() 함수로 리다이렉트
            startActivityForResult(intent, HttpURLConnection.HTTP_OK)
        }




//        checkForSignIn()

    }

    // ViewModel 세팅 및 초기화
    private fun init() {
        sharedPreference = SharedPreference(application)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        progressbarDialog = ProgressbarDialog(this)
        userViewModel.getUsersCount()
    }

    private fun initViewModelFun(){
        userViewModel.userCount.observe(this, Observer {
            login_tv_users_count.text = it.toString()
        })
        userViewModel.accessToken.observe(this, Observer {
            it
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = intent!!.data
        // 1회용 접속 Code
        val code = uri.toString().split("=")[1]
        getAccessToken(code)
    }

//    private fun checkForSignIn() {
//        progressbarDialog.show()
//        val accessToken = authToken.getToken(BuildConfig.PREFERENCES_TOKEN_KEY)
//        if (accessToken != getString(R.string.no_token)) {
//            viewModel.githubUserApi(accessToken).observe(this, Observer { body ->
//                if (body.code == HttpURLConnection.HTTP_OK) {
//                    goToMainActivity()
//                } else {
//                    longToast(getString(R.string.fail_token))
//                    progressbarDialog.hide()
//                }
//            })
//        } else {
//            longToast(getString(R.string.welcome_app))
//            progressbarDialog.hide()
//        }
//    }

    private fun getAccessToken(code:String){
        userViewModel.getAccessTokenFromGithubApi(code)
//        viewModel.getGithubCode(BuildConfig.GITHUB_CLIENT_ID, BuildConfig.GITHUB_CLIENT_SECRET,code)
//            .observe(this, Observer { body ->
//                if(body.code == HttpURLConnection.HTTP_OK) {
//                    authToken.editToken(BuildConfig.PREFERENCES_TOKEN_KEY, body.access_token)
//                    goToMainActivity()
//                }else{
//                    longToast(getString(R.string.invalid_token))
//                    progressbarDialog.hide()
//                }
//            })
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        progressbarDialog.hide()
        finish()
    }
}
