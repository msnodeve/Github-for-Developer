package com.seok.gitfordeveloper.retrofit.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

data class GithubApiUserInfo (
    val login: String,
    val id: Long,
    val node_id: String,
    val avatar_url: String,
    val gravatar_id: String,
    val url: String,
    val html_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val starred_url: String,
    val subscriptions_url: String,
    val organizations_url: String,
    val repos_url: String,
    val events_url: String,
    val receivedEvents_url: String,
    val type: String,
    val site_admin: Boolean,
    val name: String,
    val company: String,
    val blog: String,
    val location: String,
    val email: Any? = null,
    val hireable: Any? = null,
    val bio: String,
    val public_repos: Long,
    val public_gists: Long,
    val followers: Long,
    val following: Long,
    val created_at: String,
    val updated_at: String
)
interface UserInfoApi {
    @GET("user")
    fun getUserInfo(@Header("Authorization") token: String): Call<GithubApiUserInfo>
}