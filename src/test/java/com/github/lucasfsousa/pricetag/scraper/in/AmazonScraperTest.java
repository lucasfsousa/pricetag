package com.github.lucasfsousa.pricetag.scraper.in;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jsoup.nodes.Document;
import org.junit.Test;

import com.github.lucasfsousa.pricetag.AbstractScraperTest;

public class AmazonScraperTest extends AbstractScraperTest {
    private final AmazonScraper scraper = new AmazonScraper();

    @Test
    public void shouldParseAmazonUrls() {
        assertTrue(scraper.matches(
                "https://www.amazon.de/FIND-Damen-Strickpullover-Schleifendetail-Herstellergr%C3%B6%C3%9Fe/dp/B074FYK94V/ref=sr_1_1?m=A3JWKAKR8XB7XF&s=apparel&ie=UTF8&qid=1516305608&sr=1-1&refinements=p_6%3AA3JWKAKR8XB7XF"));
        assertTrue(scraper.matches(
                "https://www.amazon.co.uk/dp/B01J2BK6CO/ref=gw_aucc_rk_static_launch_0118?pf_rd_p=c83d206d-ce34-431d-b9d1-2e09a38ca075&pf_rd_r=TQV4CM3K9VA5DGE10SFH"));
        assertTrue(scraper.matches(
                "https://www.amazon.com.br/Paperwhite-ilumina%C3%A7%C3%A3o-embutida-sens%C3%ADvel-defini%C3%A7%C3%A3o/dp/B00QJDONQY/ref=lp_17387223011_1_1?s=digital-text&ie=UTF8&qid=1516305671&sr=1-1"));
    }

    @Test
    public void shouldParseAmazonDeProduct() throws Exception {
        final Document document = getDocument("in/amazon_de", "http://amazon.de/something");
        assertEquals("EUR 324.99", scraper.getPrice(document));
        assertEquals("Nintendo Switch", scraper.getTitle(document));
        assertEquals("Nintendo", scraper.getBrand(document));
        assertEquals(5, scraper.getImages(document).size());
        assertEquals("Spanisch, Deutsch, Italienisch, Französisch", scraper.getMetadata(document).get("Language"));
        assertEquals("Amazon DE", scraper.getStore(document));
        assertEquals("DE", scraper.getCountryCode(document));
    }

    @Test
    public void shouldParseAmazonComProduct() throws Exception {
        final Document document = getDocument("in/amazon_com", "http://amazon.com/something");
        // FIXME: there is a problem to identify the cents
        assertEquals("64", scraper.getPrice(document));
        assertEquals("New Super Mario Bros. U + New Super Luigi U - Wii U", scraper.getTitle(document));
        assertEquals(2, scraper.getImages(document).size());
        assertEquals("B012F20ZY6", scraper.getMetadata(document).get("ASIN"));
        assertEquals("Amazon US", scraper.getStore(document));
        assertEquals("US", scraper.getCountryCode(document));
    }

    @Test
    public void shouldParseAmazonBrProduct() throws Exception {
        final Document document = getDocument("in/amazon_br", "http://amazon.com.br/something");
        assertEquals("R$ 31,90", scraper.getPrice(document));
        assertEquals("Sapiens - Uma Breve História da Humanidade", scraper.getTitle(document));
        // FIXME: there is a problem to identify certain images
        assertEquals(0, scraper.getImages(document).size());
        assertEquals("Português", scraper.getMetadata(document).get("Idioma"));
        assertEquals("Amazon BR", scraper.getStore(document));
        assertEquals("BR", scraper.getCountryCode(document));
    }

    @Test
    public void shouldParseAmazonUkProduct() throws Exception {
        final Document document = getDocument("in/amazon_uk", "http://amazon.co.uk/something");
        assertEquals("£24.99", scraper.getPrice(document));
        assertEquals(
                "Yogaw Laptop Backpack 15.6\" Lightweight Daypacks for School Business Multipurpose Rucksacke have USB Charging port for phone– Grey",
                scraper.getTitle(document));
        assertEquals(5, scraper.getImages(document).size());
        assertEquals("B072Q4LK2F", scraper.getMetadata(document).get("ASIN"));
        assertEquals("Amazon UK", scraper.getStore(document));
        assertEquals("UK", scraper.getCountryCode(document));
    }
}
