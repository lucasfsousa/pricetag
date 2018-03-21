package com.github.lucasfsousa.pricetag;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import com.github.lucasfsousa.pricetag.scraper.Scraper;
import com.github.lucasfsousa.pricetag.scraper.in.AmazonScraper;

public class PriceTagTest {
    @Test
    public void shouldFindValidScraper() throws Exception {
        final PriceTag priceTag = new PriceTag();
        Scraper scraper = priceTag.getScraperFromUrl("https://www.amazon.de/Nintendo-2227140-Pokemon-Omega-Rubin/dp/B00MNOLAUK");
        assertNotNull(scraper);
        assertTrue(scraper instanceof AmazonScraper);
    }

    @Test(expected = ScraperNotFound.class)
    public void shouldThrowScraperNotFound() throws Exception {
        final PriceTag priceTag = new PriceTag(Collections.emptyList());
        priceTag.process("http://someurl");
    }
}
