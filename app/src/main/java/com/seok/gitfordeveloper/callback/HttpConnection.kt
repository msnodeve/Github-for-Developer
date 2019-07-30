package com.seok.gitfordeveloper.callback

import okhttp3.*


class HttpConnection {

    private var okHttpClient : OkHttpClient
    companion object {
        var httpConnection : HttpConnection = HttpConnection()
        fun getInstance() : HttpConnection{
            return httpConnection
        }
    }

    private constructor(){
        okHttpClient = OkHttpClient()
    }

    public fun requestWebServer(param1 : String, param2 : String, param3 : String, callback: Callback){
        val body = FormBody.Builder()
            .add("client_id", param1)
            .add("client_secret", param2)
            .add("code", param3)
            .build()
        val request = Request.Builder()
            .url("https://github.com/login/oauth/access_token")
            .post(body)
            .build()
        okHttpClient.newCall(request).enqueue(callback)
    }
}