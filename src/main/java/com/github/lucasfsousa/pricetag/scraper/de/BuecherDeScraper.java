package com.github.lucasfsousa.pricetag.scraper.de;

import com.github.lucasfsousa.pricetag.ParseException;
import com.github.lucasfsousa.pricetag.scraper.Scraper;
import org.jsoup.nodes.Document;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class BuecherDeScraper extends Scraper {
    @Override public boolean matches(String url) {
        return url.matches("https?:\\/\\/(www\\.)?buecher\\.de.*");
    }

    @Override protected String getStore(Document document) throws ParseException {
        return "Bücher.de";
    }

    @Override protected String getCountryCode(Document document) throws ParseException {
        return Locale.GERMANY.getCountry();
    }

    @Override protected String getBrand(Document document) throws ParseException {
        // can be removed when issue #24 is resolved
        return "";
    }

    @Override protected String getTitle(Document document) throws ParseException {
        return getMetaContent(document, "og:title");
    }

    @Override protected String getPrice(Document document) throws ParseException {
        return document.select("div.price.roboto").text().trim();
    }

    @Override protected List<String> getImages(Document document) throws ParseException {
        return Collections.singletonList(getMetaContent(document, "og:image"));
    }

    @Override protected Map<String, String> getMetadata(Document document) throws ParseException {
        return document.select("#content_product > ul.plain > li")
                       .stream()
                       .map(element -> element.text())
                       // both keys and values can contain whitespace and are delimited by a colon
                       .filter(text -> text.matches("^.+:.+$"))
                       .map(metadataString -> metadataString.split(":"))
                       .collect(Collectors.toMap(
                               (splittedMetadataString -> splittedMetadataString[0].trim()),
                               (splittedMetadataString -> splittedMetadataString[1].trim())
                       ));
    }
}
