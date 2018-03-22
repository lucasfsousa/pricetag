package com.github.lucasfsousa.pricetag.scraper.br;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;

import com.github.lucasfsousa.pricetag.ParseException;
import com.github.lucasfsousa.pricetag.scraper.Scraper;

public class B2WScraper extends Scraper {

    @Override
    public boolean matches(final String url) {
        return url.matches("https?:\\/\\/(www\\.)?(americanas|submarino|shoptime)\\.com\\.br.*");
    }

    @Override
    protected String getStore(Document document) {
        return document.select("#h_brand").text();
    }

    @Override
    protected String getCountryCode(Document document) {
        return BRAZIL;
    }

    @Override
    protected String getBrand(Document document) throws ParseException {
        return getMetadata(document).get("Marca");
    }

    @Override
    protected String getTitle(final Document document) throws ParseException {
        return document.select(".product-name").text();
    }

    @Override
    protected String getPrice(final Document document) throws ParseException {
        return document.select(".main-price .sales-price").text().replaceAll("\\.", "");
    }

    @Override
    protected List<String> getImages(Document document) {
        return document.select(".gallery-content figure img").stream()
                .map(e -> e.attr("src").isEmpty() ? e.attr("data-src") : e.attr("src")).collect(Collectors.toList());
    }

    @Override
    protected Map<String, String> getMetadata(Document document) throws ParseException {
        return document.select(".info-section tr").stream().map(e -> e.select("td")).filter(e -> e.size() == 2)
                .collect(Collectors.toMap(e -> e.get(0).html(), e -> e.get(1).html()));
    }

}
