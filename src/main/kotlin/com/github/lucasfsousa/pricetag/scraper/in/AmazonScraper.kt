package com.github.lucasfsousa.pricetag.scraper.`in`

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.lucasfsousa.pricetag.scraper.ParseException
import com.github.lucasfsousa.pricetag.scraper.Scraper
import org.jsoup.nodes.Document
import java.io.IOException
import java.util.*
import java.util.regex.Pattern

class AmazonScraper : Scraper() {

    override fun matches(url: String): Boolean = url.matches(REGEX_URL.pattern().toRegex())

    override fun getUrl(document: Document): String {
        val canonicalUrl = document.select("link [rel=canonical]").attr("href")
        return if (canonicalUrl.isEmpty()) document.baseUri() else canonicalUrl
    }

    override fun getStore(document: Document): String  = "Amazon " + getCountryCode(document)

    @Throws(ParseException::class)
    override fun getCountryCode(document: Document): String {
        val matcher = REGEX_URL.matcher(getUrl(document))
        if (matcher.matches()) {
            var countryCode = "US"
            if (matcher.group(3) != null) {
                countryCode = matcher.group(3).toUpperCase()
            }
            return countryCode
        }
        throw ParseException("Error getting country")
    }

    override fun getBrand(document: Document): String = document.select("#bylineInfo").text()

    override fun getTitle(document: Document): String = document.select("#productTitle").text()

    override fun getPrice(document: Document): String = parseText(
            document,
            "#priceblock_ourprice .buyingPrice",
            "#priceblock_ourprice",
            "#priceblock_dealprice",
            ".offer-price",
            "#priceblock_saleprice"
    )


    @Throws(ParseException::class)
    override fun getImages(document: Document): List<String> {
        val jsonImages = document.select("#imgTagWrapperId img").attr("data-a-dynamic-image")

        if (!jsonImages.isEmpty()) {
            val typeRef = object : TypeReference<HashMap<String, Any>>() {

            }

            val mapper = ObjectMapper()
            try {
                val value = mapper.readValue<Map<String, Any>>(jsonImages, typeRef)
                return ArrayList(value.keys)
            } catch (e: IOException) {
                throw ParseException("Error parsing images")
            }

        }
        return emptyList()
    }

    override fun getMetadata(document: Document): Map<String, String> {
        if (document.select("#productDetails_detailBullets_sections1").isEmpty()) {
            val pattern = "([^:]+):\\s*(.+)".toPattern()
            val map = HashMap<String, String>()

            val elements = document.select("#detail_bullets_id .content li")
            for (element in elements) {
                val matcher = pattern.matcher(element.text())
                if (matcher.matches()) {
                    map[matcher.group(1)] = matcher.group(2)
                }
            }

            return map
        } else {
            return document.select("#productDetails_detailBullets_sections1 tr")
                    .map { it.select("th").text() to it.select("td").text() }
                    .groupBy( { it.first }, { it.second})
                    .mapValues { it.value.first() }
        }
    }

    companion object {
        private val REGEX_URL = "https?:\\/\\/(www\\.)?amazon(\\.com?)?\\.?([a-z]{2})?.*".toPattern()
    }

}