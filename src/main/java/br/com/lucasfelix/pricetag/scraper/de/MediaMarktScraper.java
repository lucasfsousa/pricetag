package br.com.lucasfelix.pricetag.scraper.de;

public class MediaMarktScraper extends SaturnScraper {

    @Override
    public boolean matches(final String url) {
        return url.matches("https?:\\/\\/(www\\.)?mediamarkt\\.de.*");
    }

}
