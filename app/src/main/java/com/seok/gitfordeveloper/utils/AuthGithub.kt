package com.seok.gitfordeveloper.utils

import okhttp3.HttpUrl
import java.net.URI

class AuthGithub{

    fun getCode(uri : String) : String{
        return URI.create(uri).query.split("=")[1]
    }
    fun getToken(response : String) : String{
        return response.split("=","&")[1]
    }
    fun buildHttpUrl(clientId : String): String{
        return HttpUrl.Builder()
            .scheme("https")
            .host("github.com")
            .addPathSegment("login")
            .addPathSegment("oauth")
            .addPathSegment("authorize")
            .addQueryParameter("client_id", clientId)
            .build().toString()
    }
}