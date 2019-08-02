package com.seok.gitfordeveloper.response

import com.seok.gitfordeveloper.retrofit.models.UserInfoService

class ResponseBody {

    private var response : retrofit2.Response<UserInfoService>

    constructor(response : retrofit2.Response<UserInfoService>){
        this.response = response
    }

    fun getLogin(): String{
        return response.body()!!.login
    }

    fun getHtmlURL(): String{
        return response.body()!!.html_url
    }

    fun getAvatarURL() : String{
        return response.body()!!.avatar_url
    }
}