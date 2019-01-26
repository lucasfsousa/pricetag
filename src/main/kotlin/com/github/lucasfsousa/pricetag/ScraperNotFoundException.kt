package com.github.lucasfsousa.pricetag

class ScraperNotFoundException(val url: String) : Exception(SCRAPER_NOT_FOUND_FOR_URL + url) {
    companion object {
        private const val SCRAPER_NOT_FOUND_FOR_URL = "Scraper not found for URL: "
    }
}