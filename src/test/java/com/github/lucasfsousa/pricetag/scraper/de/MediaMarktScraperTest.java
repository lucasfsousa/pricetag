package com.github.lucasfsousa.pricetag.scraper.de;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jsoup.nodes.Document;
import org.junit.Test;

import com.github.lucasfsousa.pricetag.AbstractScraperTest;
import com.github.lucasfsousa.pricetag.scraper.de.MediaMarktScraper;

public class MediaMarktScraperTest extends AbstractScraperTest {
    private final MediaMarktScraper scraper = new MediaMarktScraper();

    @Test
    public void shouldParseMediaMarktUrl() {
        assertTrue(scraper.matches(
                "http://www.mediamarkt.de/de/product/_tom-clancy-s-rainbow-six-siege-action-playstation-4-1982224.html"));
        assertFalse(scraper.matches(
                "http://www.saturn.de/de/product/_asus-geforce-gtx-1070-rog-strix-oc-8gb-gaming-90yv09n0-m0na00-2154902.html"));
    }

    @Test
    public void shouldParseMediaMarktProduct() throws Exception {
        final Document document = getDocument("de/mediamarkt");
        assertEquals("35.99", scraper.getPrice(document));
        assertEquals("The Witcher 3 - Wild Hunt (Game of the Year Edition) [PlayStation 4]",
                scraper.getTitle(document));
        assertEquals("Media Markt", scraper.getStore(document));
        assertEquals("DE", scraper.getCountryCode(document));
        assertEquals("BANDAI NAMCO GAMES GERMANY GMB", scraper.getBrand(document));
        assertEquals(29, scraper.getImages(document).size());
        assertEquals("3391891989947", scraper.getMetadata(document).get("EAN"));
    }
}
