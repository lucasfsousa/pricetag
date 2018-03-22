package com.github.lucasfsousa.pricetag.scraper.de;

public class MediaMarktScraper extends MediaMarktSaturnScraper {

    @Override
    public boolean matches(final String url) {
        return url.matches("https?:\\/\\/(www\\.)?mediamarkt\\.de.*");
    }
}
