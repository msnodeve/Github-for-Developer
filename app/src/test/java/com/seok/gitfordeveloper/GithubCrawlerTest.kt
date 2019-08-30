package com.seok.gitfordeveloper

import android.app.Application
import android.util.Log
import com.seok.gitfordeveloper.database.Commits
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.junit.Test

class GithubCrawlerTest {

    @Test
    fun getCommitCrawler() {
        val doc = Jsoup.connect("https://github.com/msnodeve").get()
        val regex = """fill=\"#[a-zA-Z0-9]{6}\" data-count=\"\d+\" data-date=\"([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))\"""".toRegex()
        var element = doc.select("g rect")
        for(e : Element in element){
            val matchResult = regex.find(e.toString())?.value?.replace("\"", "")
            val split: List<String> = matchResult?.split(" ")!!
            val commit = Commits(split[2].split("=")[1],
                split[1].split("=")[1].toInt(),
                split[0].split("=")[1])
        }
    }
}