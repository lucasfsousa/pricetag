package com.github.lucasfsousa.pricetag.scraper.de

import com.github.lucasfsousa.pricetag.scraper.Scraper
import org.jsoup.nodes.Document
import java.util.*

abstract class MediaMarktSaturnScraper : Scraper() {

    abstract fun getUrlRegex(): Regex

    override fun matches(url: String): Boolean = url.matches(getUrlRegex())

    override fun getUrl(document: Document): String = getMetaContent(document, "og:url")

    override fun getStore(document: Document): String = getMetaContent(document, "og:site_name")

    override fun getCountryCode(document: Document): String = Locale.GERMANY.country

    override fun getBrand(document: Document): String? = getMetaContent(document, "product:brand")

    override fun getTitle(document: Document): String = getMetaContent(document, "og:title")

    override fun getPrice(document: Document): String = getMetaContent(document, "product:price:amount")

    override fun getImages(document: Document): List<String> =
            document.select(".thumbs img, .thumbnail-carousel img").map { it.attr("src") }
                .toList()

    override fun getMetadata(document: Document): Map<String, String> {
        val map = HashMap<String, String>()
        val elements = document.select("#features section .specification dt, #features section .specification dd")
        var i = 0
        while (i < elements.size) {
            map[elements[i].text().replace(":".toRegex(), "")] = elements[i + 1].text()
            i += 2
        }
        return map
    }
}

class MediaMarktScraper : MediaMarktSaturnScraper() {
    override fun getUrlRegex(): Regex = "https?:\\/\\/(www\\.)?mediamarkt\\.de.*".toRegex()
}

class SaturnScraper : MediaMarktSaturnScraper() {
    override fun getUrlRegex(): Regex = "https?:\\/\\/(www\\.)?saturn\\.de.*".toRegex()
}