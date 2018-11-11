package com.github.lucasfsousa.pricetag.scraper;

import com.github.lucasfsousa.pricetag.ParseException;
import com.github.lucasfsousa.pricetag.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Scraper {
    public static final String BRAZIL = "BR";

    public abstract boolean matches(String url);

    public Product process(final String url) throws ParseException {
        if (!matches(url)) {
            throw new ParseException("URL is not valid for this parser");
        }

        try {
            final Document document = Jsoup.connect(url).get();
            return parse(document);
        } catch (final IOException e) {
            throw new ParseException("Error connecting to url, please try again");
        }
    }

    private Product parse(final Document document) throws ParseException {
        final String priceText = getPrice(document);

        try {
            final BigDecimal price = new BigDecimal(priceText.replaceAll("[^0-9.,]", "").replaceAll(",", "."));

            Product product = new Product();
            product.setStore(getStore(document));
            product.setCountryCode(getCountryCode(document));
            product.setBrand(getBrand(document));
            product.setUrl(getUrl(document));
            product.setTitle(getTitle(document));
            product.setPriceAsText(priceText);
            product.setPrice(price);
            product.setImages(getImages(document));
            product.setMetadata(getMetadata(document));

            return product;
        } catch (final NumberFormatException e) {
            throw new ParseException("Error formating price '" + priceText + "'");
        }
    }

    protected String getUrl(Document document) throws ParseException {
        return document.baseUri();
    }

    protected abstract String getStore(final Document document) throws ParseException;

    protected abstract String getCountryCode(final Document document) throws ParseException;

    protected abstract String getBrand(final Document document) throws ParseException;

    protected abstract String getTitle(final Document document) throws ParseException;

    protected abstract String getPrice(final Document document) throws ParseException;

    protected abstract List<String> getImages(Document document) throws ParseException;

    protected Map<String, String> getMetadata(Document document) throws ParseException {
        return new HashMap<>();
    }

    protected String parseText(final Document document, final String... queries) throws ParseException {
        for (final String query : queries) {
            final Element element = document.select(query).first();
            if (element != null && element.hasText()) {
                return element.text();
            }
        }
        throw new ParseException("Error parsing document, query: " + Arrays.toString(queries));
    }

    protected String getMetaContent(Document document, String property) {
        return document.select("meta[property=" + property + "]").attr("content");
    }
}
