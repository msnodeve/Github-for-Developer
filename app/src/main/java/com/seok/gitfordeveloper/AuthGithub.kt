package com.seok.gitfordeveloper

import android.net.Uri
import okhttp3.HttpUrl
import java.net.URI

class AuthGithub{

    public fun getCode(uri : String) : String{
        return URI.create(uri).query.split("=")[1]
    }

    public fun buildHttpUrl(clientId : String): String{
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