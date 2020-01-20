package com.seok.gfd

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Before
import org.junit.Test


@Suppress("CAST_NEVER_SUCCEEDS")
class CrawlerTest{

    private lateinit var response: Connection.Response
    private lateinit var soup: Document


    @Before
    fun getHtmlFromGithub(){
        response = Jsoup.connect("https://github.com/msnodeve")
            .method(Connection.Method.GET)
            .execute()

        soup = response.parse()
    }

    @Test
    fun contributionCrawlingFromGithubTest(){
        val partOfContributionData = soup.select("div[class=js-yearly-contributions]").first()
        val lines = partOfContributionData.select("rect[class=day]")

        for(line in lines){
            val attrDataDate = line.attr("data-date")
            val attrDataCount = line.attr("data-count")
            val attrFill = line.attr("fill")
            println("data-date=$attrDataDate, data-count=$attrDataCount, fill=$attrFill")
        }
    }

    @Test
    fun contributionCrawlingYearFromGithubTest(){
        val contributionYearData = soup.select("h2[class=f4 text-normal mb-2]").first()
        val contribution = contributionYearData.text().split(" ")
        println( contribution[0])
    }

}