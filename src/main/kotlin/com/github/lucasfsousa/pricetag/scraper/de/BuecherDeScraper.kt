package com.github.lucasfsousa.pricetag.scraper.de

import com.github.lucasfsousa.pricetag.scraper.Scraper
import org.jsoup.nodes.Document
import java.util.*

class BuecherDeScraper : Scraper() {
    override fun matches(url: String): Boolean = url.matches("https?:\\/\\/(www\\.)?buecher\\.de.*".toRegex())

    override fun getStore(document: Document): String = "Bücher.de"

    override fun getCountryCode(document: Document): String = Locale.GERMANY.country

    override fun getBrand(document: Document): String? = null

    override fun getTitle(document: Document): String = getMetaContent(document, "og:title")

    override fun getPrice(document: Document): String = document.select("div.price.roboto").text().trim { it <= ' ' }

    override fun getImages(document: Document): List<String> = listOf(getMetaContent(document, "og:image"))

    override fun getMetadata(document: Document): Map<String, String> {
        return document.select("#content_product > ul.plain > li")
                .map { element -> element.text() }
                // both keys and values can contain whitespace and are delimited by a colon
                .filter { text -> text.matches("^.+:.+$".toRegex()) }
                .map { metadataString -> metadataString.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() }
                .groupBy({ splittedMetadataString -> splittedMetadataString[0].trim { it <= ' ' } }, { splittedMetadataString -> splittedMetadataString[1].trim { it <= ' ' } })
                .mapValues { it.value.first() }
    }
}