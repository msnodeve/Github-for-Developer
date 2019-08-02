package com.seok.gitfordeveloper

import com.seok.gitfordeveloper.response.ResponseBody
import com.seok.gitfordeveloper.retrofit.models.UserInfoService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.stubbing.OngoingStubbing

class ResponseBodyTest {

    @Mock
    private lateinit var response : retrofit2.Response<UserInfoService>


    @Before
    fun setup(){
        var tempUserInfoService = UserInfoService(login = "test",html_url = "test",avatar_url = "test")
        if(::response.isInitialized) {
            //
        }
    }

    @Test
    fun UserInfoServicegetLoginTest(){
        var responseBody = ResponseBody(response)
        Assert.assertEquals(responseBody.getLogin(), "test")
    }
}
