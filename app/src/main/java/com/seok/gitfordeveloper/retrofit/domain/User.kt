package com.seok.gitfordeveloper.retrofit.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

@Getter
@Setter
@NoArgsConstructor
class User {
    var login: String = ""
    var id: Long = 0
    var node_id: String = ""
    var avatar_url: String = ""
    var gravatar_id: String = ""
    var url: String = ""
    var html_url: String = ""
    var followers_url: String = ""
    var following_url: String = ""
    var gists_url: String = ""
    var starred_url: String = ""
    var subscriptions_url: String = ""
    var organizations_url: String = ""
    var repos_url: String = ""
    var events_url: String = ""
    var receivedEvents_url: String = ""
    var type: String = ""
    var site_admin: Boolean = false
    var name: String = ""
    var company: String = ""
    var blog: String = ""
    var location: String = ""
    var email: Any? = null
    var hireable: Any? = null
    var bio: String = ""
    var public_repos: Long = 0
    var public_gists: Long = 0
    var followers: Long = 0
    var following: Long = 0
    var created_at: String = ""
    var updated_at: String = ""
    var code: Int = 0

    constructor(login: String, html_url: String, avatar_url: String, code : Int) {
        this.login = login
        this.html_url = html_url
        this.avatar_url = avatar_url
        this.code = code
    }
    constructor(login: String, html_url: String, avatar_url: String){
        this.login = login
        this.html_url = html_url
        this.avatar_url = avatar_url
    }
    constructor(code : Int){
        this.code = code
    }
}