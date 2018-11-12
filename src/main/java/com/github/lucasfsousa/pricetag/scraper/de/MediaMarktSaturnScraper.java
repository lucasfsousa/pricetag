package com.github.lucasfsousa.pricetag.scraper.de;

import com.github.lucasfsousa.pricetag.ParseException;
import com.github.lucasfsousa.pricetag.scraper.Scraper;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class MediaMarktSaturnScraper extends Scraper {

    @Override
    protected String getUrl(Document document) {
        return getMetaContent(document, "og:url");
    }

    @Override
    protected String getStore(Document document) {
        return getMetaContent(document, "og:site_name");
    }

    @Override
    protected String getCountryCode(Document document) {
        return Locale.GERMANY.getCountry();
    }

    @Override
    protected String getBrand(Document document) throws ParseException {
        return getMetaContent(document, "product:brand");
    }

    @Override
    protected String getTitle(final Document document) throws ParseException {
        return getMetaContent(document, "og:title");
    }

    @Override
    protected String getPrice(final Document document) throws ParseException {
        return getMetaContent(document, "product:price:amount");
    }

    @Override
    protected List<String> getImages(Document document) {
        return document.select(".thumbs img, .thumbnail-carousel img").stream().map(e -> e.attr("src"))
                .collect(Collectors.toList());
    }

    @Override
    protected Map<String, String> getMetadata(Document document) throws ParseException {
        Map<String, String> map = new HashMap<>();
        Elements elements = document.select("#features section .specification dt, #features section .specification dd");
        for (int i = 0; i < elements.size(); i += 2) {
            map.put(elements.get(i).text().replaceAll(":", ""), elements.get(i + 1).text());
        }
        return map;
    }
}
