package com.github.lucasfsousa.pricetag.scraper.de;

import com.github.lucasfsousa.pricetag.scraper.Scraper;
import org.jsoup.nodes.Document;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameStopDeScraper extends Scraper {
    @Override
    public boolean matches(String url) {
        return url.matches("https?:\\/\\/(www\\.)?gamestop\\.de.*");
    }

    @Override
    protected String getStore(Document document) {
        return "GameStop DE";
    }

    @Override
    protected String getCountryCode(Document document) {
        return "DE";
    }

    @Override
    protected String getBrand(Document document) {
        return document.select(".prodTitle > p strong").text();
    }

    @Override
    protected String getTitle(Document document) {
        //We need to remove HTML spaces for the title
        return document.select(".prodTitle h1 span").text().replace("\u00a0","").trim();
    }

    @Override
    protected String getPrice(Document document) {
        return document.select(".valuteCont").text();
    }

    @Override
    protected List<String> getImages(Document document) {
        return document.select(".suggestedPackShots a").stream().map(e -> e.attr("href")).collect(Collectors.toList());
    }

    @Override
    protected Map<String, String> getMetadata(Document document) {
        return document.select(".addedDetInfo p").stream()
                .map(e -> new AbstractMap.SimpleEntry(e.select("label").text(), e.select("span").text()))
                .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString()));
    }
}
