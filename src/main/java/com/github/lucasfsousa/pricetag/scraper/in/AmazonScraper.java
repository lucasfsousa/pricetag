package com.github.lucasfsousa.pricetag.scraper.in;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lucasfsousa.pricetag.ParseException;
import com.github.lucasfsousa.pricetag.scraper.Scraper;

public class AmazonScraper extends Scraper {
    private static final Pattern REGEX_URL = Pattern.compile("https?:\\/\\/(www\\.)?amazon(\\.com?)?\\.?([a-z]{2})?.*");

    @Override
    public boolean matches(final String url) {
        return url.matches(REGEX_URL.pattern());
    }

    @Override
    protected String getUrl(Document document) throws ParseException {
        String canonicalUrl = document.select("link [rel=canonical]").attr("href");
        if (canonicalUrl.isEmpty()) {
            return document.baseUri();
        }
        return canonicalUrl;
    }

    @Override
    protected String getStore(Document document) throws ParseException {
        return "Amazon " + getCountryCode(document);
    }

    @Override
    protected String getCountryCode(Document document) throws ParseException {
        Matcher matcher = REGEX_URL.matcher(getUrl(document));
        if (matcher.matches()) {
            String countryCode = "US";
            if (matcher.group(3) != null) {
                countryCode = matcher.group(3).toUpperCase();
            }
            return countryCode;
        }
        return null;
    }

    @Override
    protected String getBrand(Document document) throws ParseException {
        return document.select("#bylineInfo").text();
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

    @Override
    protected List<String> getImages(Document document) {
        String jsonImages = document.select("#imgTagWrapperId img").attr("data-a-dynamic-image");

        if (!jsonImages.isEmpty()) {
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
            };

            ObjectMapper mapper = new ObjectMapper();
            try {
                Map<String, Object> value = mapper.readValue(jsonImages, typeRef);
                return new ArrayList<>(value.keySet());
            } catch (IOException e) {
                System.err.println("Error parsing images: " + e.getMessage());
            }
        }
        return Collections.emptyList();
    }

    @Override
    protected Map<String, String> getMetadata(Document document) throws ParseException {
        if (document.select("#productDetails_detailBullets_sections1").isEmpty()) {
            Pattern pattern = Pattern.compile("([^:]+):\\s*(.+)");
            Map<String, String> map = new HashMap<>();

            Elements elements = document.select("#detail_bullets_id .content li");
            for (Element element : elements) {
                Matcher matcher = pattern.matcher(element.text());
                if (matcher.matches()) {
                    map.put(matcher.group(1), matcher.group(2));
                }
            }

            return map;
        } else {
            return document.select("#productDetails_detailBullets_sections1 tr").stream()
                    .collect(Collectors.toMap(e -> e.select("th").text(), e -> e.select("td").text()));
        }
    }

}
