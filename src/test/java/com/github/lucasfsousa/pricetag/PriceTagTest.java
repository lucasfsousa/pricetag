package com.github.lucasfsousa.pricetag;

import java.util.Collections;

import org.junit.Test;

import com.github.lucasfsousa.pricetag.PriceTag;
import com.github.lucasfsousa.pricetag.ScraperNotFound;

public class PriceTagTest {
    @Test
    public void shouldParseValidUrl() throws Exception {
        final PriceTag priceTag = new PriceTag();
        priceTag.process("https://www.amazon.de/Nintendo-2227140-Pokemon-Omega-Rubin/dp/B00MNOLAUK");
    }

    @Test(expected = ScraperNotFound.class)
    public void shouldThrowScraperNotFound() throws Exception {
        final PriceTag priceTag = new PriceTag(Collections.emptyList());
        priceTag.process("http://someurl");
    }
}
