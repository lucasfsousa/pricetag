package com.github.lucasfsousa.pricetag.scraper.br

import com.github.lucasfsousa.pricetag.scraper.Scraper
import org.jsoup.nodes.Document

class B2WScraper : Scraper() {

    override fun matches(url: String): Boolean = url.matches(REGEX_URL)

    override fun getStore(document: Document): String = document.select("#h_brand").text()

    override fun getCountryCode(document: Document): String = Scraper.BRAZIL

    override fun getBrand(document: Document): String? = getMetadata(document)["Marca"]

    override fun getTitle(document: Document): String = document.select(".product-name").text()

    override fun getPrice(document: Document): String = document.select(".main-price .sales-price").text()
            .replace("\\.".toRegex(), "")

    override fun getImages(document: Document): List<String> = document.select(".gallery-content figure img")
            .map { if (it.attr("src").isEmpty()) it.attr("data-src") else it.attr("src") }
            .toList()

    override fun getMetadata(document: Document): Map<String, String> {
        return document.select(".info-section tr")
                .map { it.select("td") }
                .filter { it.size == 2 }
                .groupBy( { it[0].html()}, { it[1].html()})
                .mapValues { it.value.first() }
    }

    companion object {
        private val REGEX_URL = "https?:\\/\\/(www\\.)?(americanas|submarino|shoptime)\\.com\\.br.*".toRegex()
    }

}