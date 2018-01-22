package com.github.lucasfsousa.pricetag.scraper.in;

import org.jsoup.nodes.Document;

import com.github.lucasfsousa.pricetag.ParseException;
import com.github.lucasfsousa.pricetag.scraper.Scraper;

public class AmazonScraper extends Scraper {
    @Override
    public boolean matches(final String url) {
        return url.matches("https?:\\/\\/(www\\.)?amazon\\..*");
    }

    @Override
    protected String getTitle(final Document document) {
        return document.select("#productTitle").text();
    }

    @Override
    protected String getPrice(final Document document) throws ParseException {
        return parseText(document, "#priceblock_ourprice .buyingPrice", "#priceblock_ourprice", "#priceblock_dealprice",
                ".offer-price", "#priceblock_saleprice");
    }
}
