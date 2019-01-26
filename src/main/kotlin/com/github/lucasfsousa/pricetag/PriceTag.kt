package com.github.lucasfsousa.pricetag

import com.github.lucasfsousa.pricetag.scraper.Scraper
import org.reflections.Reflections
import java.lang.reflect.Modifier
import java.util.ArrayList

class PriceTag {
    private val scrapers: List<Scraper>

    constructor() {
        scrapers = getScrapers()
    }

    constructor(scrapers: List<Scraper>) {
        this.scrapers = scrapers
    }

    @Throws(ScraperNotFoundException::class)
    protected fun getScraperFromUrl(url: String): Scraper {
        return scrapers.stream().filter { s -> s.matches(url) }.findAny()
                .orElseThrow { ScraperNotFoundException(url) }
    }

    @Throws(ScraperNotFoundException::class)
    fun process(url: String): Product {
        return getScraperFromUrl(url).process(url)
    }

    private fun getScrapers(): List<Scraper> {
        val reflections = Reflections("com.github.lucasfsousa.pricetag.scraper")
        val scraperClasses = reflections.getSubTypesOf(Scraper::class.java)
        val scrapers = ArrayList<Scraper>()
        for (c in scraperClasses) {
            if (isInstantiable(c)) {
                try {
                    scrapers.add(c.newInstance())
                } catch (e: InstantiationException) {
                    throw IllegalStateException("Error creating instance of: " + c.name, e)
                } catch (e: IllegalAccessException) {
                    throw IllegalStateException("Error creating instance of: " + c.name, e)
                }

            }
        }
        return scrapers
    }

    private fun isInstantiable(c: Class<*>): Boolean = !(Modifier.isAbstract(c.modifiers) || c.isInterface)

}