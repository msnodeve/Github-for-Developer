package com.seok.gitfordeveloper

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class DateTimeTest {

    @Test
    fun dateTimeFormatTest() {
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val output = formatter.format(parser.parse("2018-12-12"))
        Assert.assertEquals(output ,"2018-12-12")
    }

    @Test
    fun todayFormatTest(){
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val output = formatter.format(parser.parse(LocalDateTime.now().toLocalDate().toString()))
        Assert.assertEquals(output ,"2019-08-29")
    }

    @Test
    fun dateTimeMillisTest(){
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val output = formatter.format(parser.parse(Date(System.currentTimeMillis()).toInstant().toString()))
        Assert.assertEquals(output ,"2019-08-29")
    }
}