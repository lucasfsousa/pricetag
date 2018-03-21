package com.github.lucasfsousa.pricetag;

public class ScraperNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;
    private static final String SCRAPER_NOT_FOUND_FOR_URL = "Scraper not found for URL: ";
    private String url;

    public ScraperNotFoundException(final String url) {
        super(SCRAPER_NOT_FOUND_FOR_URL + url);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
