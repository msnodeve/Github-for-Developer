package com.seok.gitfordeveloper

import org.junit.Assert
import org.junit.Test


class LoginActivityUnitTest {
    // 1. base64 encoding decoding class, fun
    // 2. curl HTTPClient retrofit okhttp3
    // 3. access token local save SharedPreference
    @Test
    fun buildHttpUrlTest() {
        val authGithub = AuthGithub()
        val result = authGithub.buildHttpUrl("testClientId")
        val testHttpUrl = "https://github.com/login/oauth/authorize?client_id=testClientId"
        Assert.assertEquals(result, testHttpUrl)
    }

    @Test
    fun test_getCode() {
        val url = "gfd://github.for.developer?code=84a6df8c50f260978039"
        val authGithub = AuthGithub()
        val code = try {
            check(url.startsWith("gfd://github.for.developer")) { "Incorrect $url" }
            authGithub.getCode(url)
        } catch (e: Exception) {
            assert(false) { e.message.toString() }
        }
        assert("84a6df8c50f260978039" == code)
    }

}