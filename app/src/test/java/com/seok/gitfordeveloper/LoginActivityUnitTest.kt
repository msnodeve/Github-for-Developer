package com.seok.gitfordeveloper

import com.seok.gitfordeveloper.utils.AuthGithub
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

    @Test
    fun test_getToken() {
        val response = "access_token=e339b92de18be7529bc27c28e3ee4d6ad7994fb7&scope=&token_type=bearer"
        val authGithub = AuthGithub()
        val token = try {
            val responses = response.split("=","&")
            check(responses[0]=="access_token"){"Incorrect $response"}
            authGithub.getToken(response)
        } catch (e: Exception) {
            assert(false) { e.message.toString() }
        }
        assert("e339b92de18be7529bc27c28e3ee4d6ad7994fb7" == token)
    }
}