package com.seok.gfd

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Test


class CrawlerTest{

    @Test
    fun test(){
        val response: Connection.Response = Jsoup.connect("https://github.com/msnodeve")
            .method(Connection.Method.GET)
            .execute()
        val document: Document = response.parse()

        val html: String = document.html()

        val select = document.select("div[class=js-yearly-contributions]").first()

        val test = select.select("rect[class=day]")

        System.out.println(test)

        for(value in test){
            System.out.println()
        }
    }
}