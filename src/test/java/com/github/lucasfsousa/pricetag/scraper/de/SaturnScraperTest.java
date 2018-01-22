package com.github.lucasfsousa.pricetag.scraper.de;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jsoup.nodes.Document;
import org.junit.Test;

import com.github.lucasfsousa.pricetag.AbstractScraperTest;
import com.github.lucasfsousa.pricetag.scraper.de.SaturnScraper;

public class SaturnScraperTest extends AbstractScraperTest {
    private final SaturnScraper scraper = new SaturnScraper();

    @Test
    public void shouldMatchSaturnUrl() {
        assertTrue(scraper.matches("http://www.saturn.de/de/product/_motorola-moto-c-plus-2289271.html"));
        assertFalse(scraper.matches(
                "http://www.mediamarkt.de/de/product/_tom-clancy-s-rainbow-six-siege-action-playstation-4-1982224.html"));
    }

    @Test
    public void shouldParseSaturnProduct() throws Exception {
        final Document document = getDocument("de/saturn");
        assertEquals("549", scraper.getPrice(document));
        assertEquals(
                "ASUS F555QG-DM188T, Notebook mit 15.6 Zoll Display, A10 Prozessor, 12 GB RAM, 1 TB HDD, Radeon™ R5 M430, Schwarz/Silber",
                scraper.getTitle(document));
    }
}
