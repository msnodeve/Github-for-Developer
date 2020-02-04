package com.seok.gfd.views


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.seok.gfd.BuildConfig
import com.seok.gfd.R
import com.seok.gfd.utils.ProgressbarDialog
import com.seok.gfd.utils.SharedPreference
import com.seok.gfd.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.longToast
import java.net.HttpURLConnection

@Suppress(
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPreference: SharedPreference
    private lateinit var progressbar : ProgressbarDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        initViewModelFun()

        // 로그인 버튼 눌렀을 경우 Github login 창으로 넘김
        login_img_login.setOnClickListener {
            progressbar.show()
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(BuildConfig.GITHUB_OAUTH_URL + BuildConfig.GITHUB_CLIENT_ID)
            )
            // onNewIntent() 리다이렉트
            startActivityForResult(intent, HttpURLConnection.HTTP_OK)
        }

        userViewModel.getUserInfoAndSignInGithub(sharedPreference.getValue(BuildConfig.PREFERENCES_TOKEN_KEY))

    }

    // ViewModel 세팅 및 초기화
    private fun init() {
        progressbar = ProgressbarDialog(this)
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
            sharedPreference.setValue(BuildConfig.PREFERENCES_TOKEN_KEY, it)
            userViewModel.getUserInfoAndSignInGithub(it)
        })
        // Github 로그인 성공 코드 200 / 401
        userViewModel.code.observe(this, Observer {
            if (it == HttpURLConnection.HTTP_OK) {
                goToMainActivity()
            } else {
                longToast(getString(R.string.fail_access_token))
            }
        })
        userViewModel.userInfo.observe(this, Observer {
            sharedPreference.setValueObject(application.getString(R.string.user_info), it)
            userViewModel.signInUserInfo(it)
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
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        progressbar.hide()
        finish()
    }
}
