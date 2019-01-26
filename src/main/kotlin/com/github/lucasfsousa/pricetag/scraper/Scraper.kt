package com.github.lucasfsousa.pricetag.scraper

import com.github.lucasfsousa.pricetag.Product
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.math.BigDecimal
import java.util.*

abstract class Scraper {

    abstract fun matches(url: String): Boolean

    @Throws(ParseException::class)
    fun process(url: String): Product {
        if (!matches(url)) {
            throw ParseException("$url is not valid for this parser")
        }

        try {
            return parse(Jsoup.connect(url).get())
        } catch (e: IOException) {
            throw ParseException("Error connecting to $url, please try again")
        }
    }

    @Throws(ParseException::class)
    private fun parse(document: Document): Product {
        val price = getPrice(document)
        try {
            return Product(
                    store = getStore(document),
                    countryCode = getCountryCode(document),
                    brand = getBrand(document),
                    url = getUrl(document),
                    title = getTitle(document),
                    priceAsText = price,
                    price = convertToBigDecimal(price),
                    images = getImages(document),
                    metadata = getMetadata(document)
            )
        } catch (e: NumberFormatException) {
            throw ParseException("Error formatting price '$price'")
        }
    }

    @Throws(ParseException::class)
    protected open fun getUrl(document: Document): String {
        return document.baseUri()
    }

    @Throws(ParseException::class)
    protected abstract fun getStore(document: Document): String

    @Throws(ParseException::class)
    protected abstract fun getCountryCode(document: Document): String

    @Throws(ParseException::class)
    protected abstract fun getBrand(document: Document): String?

    @Throws(ParseException::class)
    protected abstract fun getTitle(document: Document): String

    @Throws(ParseException::class)
    protected abstract fun getPrice(document: Document): String

    @Throws(ParseException::class)
    protected abstract fun getImages(document: Document): List<String>

    @Throws(ParseException::class)
    protected open fun getMetadata(document: Document): Map<String, String> {
        return HashMap()
    }

    @Throws(ParseException::class)
    protected fun parseText(document: Document, vararg queries: String): String {
        for (query in queries) {
            val element = document.select(query).first()
            if (element != null && element.hasText()) {
                return element.text()
            }
        }
        throw ParseException("Error parsing document, query: " + Arrays.toString(queries))
    }

    protected fun getMetaContent(document: Document, property: String): String {
        return document.select("meta[property=$property]").attr("content")
    }

    private fun convertToBigDecimal(price: String): BigDecimal =
            BigDecimal(price.replace("[^0-9.,]".toRegex(), "").replace(",".toRegex(), "."))

    companion object {
        @JvmField
        val BRAZIL = "BR"
    }
}

class ParseException(message: String) : Exception(message)