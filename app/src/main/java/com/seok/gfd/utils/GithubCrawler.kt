package com.seok.gfd.utils

import com.seok.gfd.database.Commits
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class GithubCrawler {

    fun getCommitCrawler(url:String) : ArrayList<Commits> {
        val doc = Jsoup.connect(url).get()
        val regex = """fill=\"#[a-zA-Z0-9]{6}\" data-count=\"\d+\" data-date=\"([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))\"""".toRegex()
        val element = doc.select("g rect")
        val commits = ArrayList<Commits>()
        for(e : Element in element) {
            val matchResult = regex.find(e.toString())?.value?.replace("\"", "")
            val split: List<String> = matchResult?.split(" ")!!
            commits.add(
                Commits(
                    split[2].split("=")[1],
                    split[1].split("=")[1].toInt(),
                    split[0].split("=")[1]
                )
            )
        }
        return commits
    }


}