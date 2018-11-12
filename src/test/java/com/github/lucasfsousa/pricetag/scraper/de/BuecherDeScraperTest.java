package com.github.lucasfsousa.pricetag.scraper.de;

import com.github.lucasfsousa.pricetag.AbstractScraperTest;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BuecherDeScraperTest extends AbstractScraperTest {

    final BuecherDeScraper scraper = new BuecherDeScraper();

    @Test
    public void shouldMatchBuecherDeUrl() {
        assertTrue(scraper.matches(
                "https://www.buecher.de/shop/englische-buecher/cracking-the-coding-interview-189-programming-questions-and-solutions/mcdowell-gayle-laakmann/products_products/detail/prod_id/43407634/"));
    }

    @Test
    public void parsesBuecherDeDocument() throws Exception {
        final Document document = getDocument("de/buecherde");
        assertEquals("Bücher.de", scraper.getStore(document));
        assertEquals("44,99", scraper.getPrice(document));
        assertEquals("DE", scraper.getCountryCode(document));
        assertEquals("Cracking the Coding Interview: 189 Programming Questions and Solutions",
                     scraper.getTitle(document));
        assertEquals(1, scraper.getImages(document).size());
        assertEquals("http://bilder.buecher.de/produkte/43/43407/43407634z.jpg",
                     scraper.getImages(document).get(0));
        Map<String, String> metadata = scraper.getMetadata(document);
        assertEquals(8, metadata.size());
        assertEquals("CareerCup", metadata.get("Verlag"));
        assertEquals("708", metadata.get("Seitenzahl"));
        assertEquals("1. Juli 2015", metadata.get("Erscheinungstermin"));
        assertEquals("251mm x 179mm x 40mm", metadata.get("Abmessung"));
        assertEquals("1382g", metadata.get("Gewicht"));
        assertEquals("9780984782857", metadata.get("ISBN-13"));
        assertEquals("0984782850", metadata.get("ISBN-10"));
        assertEquals("43407634", metadata.get("Artikelnr."));
    }
}
