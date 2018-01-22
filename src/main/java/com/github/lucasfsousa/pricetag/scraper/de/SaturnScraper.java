package com.github.lucasfsousa.pricetag.scraper.de;

import org.jsoup.nodes.Document;

import com.github.lucasfsousa.pricetag.ParseException;
import com.github.lucasfsousa.pricetag.scraper.Scraper;

public class SaturnScraper extends Scraper {

    @Override
    protected String getTitle(final Document document) throws ParseException {
        return document.select("h1[itemprop=name]").text();
    }

    @Override
    protected String getPrice(final Document document) throws ParseException {
        return document.select("meta[itemprop=price]").attr("content");
    }

    @Override
    public boolean matches(final String url) {
        return url.matches("https?:\\/\\/(www\\.)?saturn\\.de.*");
    }

}
