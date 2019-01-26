package com.github.lucasfsousa.pricetag.scraper.de

import com.github.lucasfsousa.pricetag.scraper.Scraper
import org.jsoup.nodes.Document
import java.util.*

class GameStopDeScraper : Scraper() {
    override fun matches(url: String): Boolean = url.matches("https?:\\/\\/(www\\.)?gamestop\\.de.*".toRegex())

    override fun getStore(document: Document): String = "GameStop DE"

    override fun getCountryCode(document: Document): String = Locale.GERMANY.country

    override fun getBrand(document: Document): String = document.select(".prodTitle > p strong").text()

    override fun getTitle(document: Document): String {
        //We need to remove HTML spaces for the title
        return document.select(".prodTitle h1 span").text().replace("\u00a0", "").trim { it <= ' ' }
    }

    override fun getPrice(document: Document): String = document.select(".valuteCont").text()

    override fun getImages(document: Document): List<String> = document.select(".suggestedPackShots a")
            .map { it.attr("href") }
            .toList()

    override fun getMetadata(document: Document): Map<String, String> = document.select(".addedDetInfo p")
            .map { it.select("label").text() to it.select("span").text() }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.first() }
}