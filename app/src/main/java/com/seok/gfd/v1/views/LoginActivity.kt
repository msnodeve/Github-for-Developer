package com.seok.gfd.v1.views


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.seok.gfd.R
import com.seok.gfd.utils.SharedPreference
import com.seok.gfd.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.longToast
import java.net.HttpURLConnection

class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        initViewModelFun()
        setOnClickFun()

    }

    // ViewModel 세팅 및 초기화
    private fun init() {
        sharedPreference = SharedPreference(application)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel.getUsersCount()
    }

    // ViewModel 구현
    private fun initViewModelFun() {
        // 현재 사용자 수
        userViewModel.userCount.observe(this, Observer {
            login_tv_users_count.text = it.toString()
        })
        // Github 접속을 위한 access_token 요청
        userViewModel.accessToken.observe(this, Observer {
            sharedPreference.setValue("", it)
            userViewModel.getUserInfoAndSignInGithub(it)
        })
        // Github 로그인 성공 코드 200 / 401
        userViewModel.code.observe(this, Observer {
            if (it == HttpURLConnection.HTTP_OK) {
                goToMainActivity()
            } else {
                login_progress_bar.visibility = View.INVISIBLE
                longToast(getString(R.string.fail_access_token))
            }
        })
        userViewModel.userInfo.observe(this, Observer {
            sharedPreference.setValueObject(application.getString(R.string.user_info), it)
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = intent!!.data
        // 1회용 접속 Code
        val code = uri.toString().split("=")[1]
        userViewModel.getAccessTokenFromGithubApi(code)
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, Main2Activity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun setOnClickFun(){
        // Guest 로그인 버튼을 눌렀을 경우
        login_img_guest.setOnClickListener {
            syncUI()
            startActivity(Intent(this, GuestMain::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        // 로그인 버튼 눌렀을 경우 Github login 창으로 넘김
        login_img_login.setOnClickListener {
            syncUI()
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("")
            )
            // onNewIntent() 리다이렉트
            startActivityForResult(intent, HttpURLConnection.HTTP_OK)
        }
    }

    private fun syncUI(){
        login_progress_bar.visibility = View.VISIBLE
        login_img_login.isClickable = false
        login_img_guest.isClickable = false
        Handler().postDelayed({
            login_progress_bar.visibility = View.INVISIBLE
            login_img_login.isClickable = true
            login_img_guest.isClickable = true
        }, 3000)
    }
}
