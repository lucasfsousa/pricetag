package com.github.lucasfsousa.pricetag.scraper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.github.lucasfsousa.pricetag.ParseException;
import com.github.lucasfsousa.pricetag.Product;

public abstract class Scraper {
    public abstract boolean matches(String url);

    protected abstract String getTitle(final Document document) throws ParseException;

    protected abstract String getPrice(final Document document) throws ParseException;

    protected Product parse(final String url, final Document document) throws ParseException {
        final String priceText = getPrice(document);

        try {
            final BigDecimal price = new BigDecimal(priceText.replaceAll("[^0-9.,]", "").replaceAll(",", "."));
            return new Product(url, getTitle(document), priceText, price);
        } catch (final NumberFormatException e) {
            throw new ParseException("Error formating price '" + priceText + "'");
        }
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

    public Product process(final String url) throws ParseException {
        if (!matches(url)) {
            throw new ParseException("URL is not valid for this parser");
        }

        try {
            final Document document = Jsoup.connect(url).get();
            return parse(url, document);
        } catch (final IOException e) {
            throw new ParseException("Error connecting to url, please try again");
        }
    }
}
