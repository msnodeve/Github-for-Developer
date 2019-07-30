package com.seok.gitfordeveloper

import android.webkit.WebView
import com.seok.gitfordeveloper.unit.GithubWebViewClient
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.Mock

class GithubUnitTest{

    @Mock
    private lateinit var webView: WebView

    private val githubWebViewClient : GithubWebViewClient? = GithubWebViewClient("git")

    @Test
    fun getHttpUrlTest(){
        var result : String = ""
        if(githubWebViewClient != null){
            result = githubWebViewClient.getHttpUrl()
        }
        assertEquals(result, "https://github.com/login/oauth/authorize?client_id=git&scope=user&allow_signup=false")
    }

    @Test
    fun shouldOverrideUrlLoadingTest() {
        //Override 된 메서드는 어떻게 테스트를 해야할까?
        //assert(githubWebViewClinet!!.shouldOverrideUrlLoading(webView, githubWebViewClinet!!.getHttpUrl())!=null)
    }
}