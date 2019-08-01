package com.seok.gitfordeveloper.github

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.seok.gitfordeveloper.MainActivity
import com.seok.gitfordeveloper.R
import com.seok.gitfordeveloper.UserInfo
import okhttp3.*
import java.io.IOException

class AuthGithub constructor(context: Context) {
    private lateinit var getCodeHttpUrl: HttpUrl
    private lateinit var context: Context

    init {
        Log.d("dLog-AGH-init", "init start")
        this.context = context
        getCodeHttpUrl = HttpUrl.Builder().scheme("https").host("github.com")
            .addPathSegment("login")
            .addPathSegment("oauth")
            .addPathSegment("authorize")
            .addQueryParameter("client_id", this.context.getString(R.string.github_app_id))
            .addQueryParameter("scope", "user:email")
            .build()
    }

    public fun redirectURLActivity(): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse(getCodeHttpUrl.toString()))
    }

    public fun sendPost(code : String) {
        val client = OkHttpClient()
        val form = makeForm(code)
        var request = makeRequest(form)
        client.newCall(request).enqueue(object : Callback{
            override fun onResponse(call: Call, response: Response) {
                Log.d("dLog-AGH-sendPost", "Success")
                // 아래와 같은 엑세스 토큰을 얻음 따라서 해당 토큰으로 깃허브 API를 받아올 수 있음.
                // e.g. Response form : access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer
                val responseBody: String = response.body()!!.string()
                var splittedBody: List<String> = responseBody.split("=", "&")
                if (splittedBody[0].equals("access_token")) {
                    signInWithToken(splittedBody[1])
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                Log.d("dLog-AGH-sendPost", e.toString())
            }
        })
    }

    private fun makeForm(code : String): FormBody{
        // token을 얻어오기 위한 form 생성 함수
        var form = FormBody.Builder()
            .add("client_id", this.context.getString(R.string.github_app_id))
            .add("client_secret", this.context.getString(R.string.github_app_secret))
            .add("code", code)
            .build()
        return form
    }

    private fun makeRequest(form : FormBody) : Request{
        // github로 request를 보내기위한 생성 함수
        val requset = Request.Builder()
            .url("https://github.com/login/oauth/access_token")
            .post(form)
            .build()
        return requset
    }

    private fun signInWithToken(token : String){
        // sendPost함수가 성공적으로 response를 받았다면 MainActivity로 전달
        UserInfo.access_token = token
        var intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}