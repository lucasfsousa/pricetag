package com.github.lucasfsousa.pricetag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.github.lucasfsousa.pricetag.scraper.Scraper;

public class PriceTag {
    private final List<Scraper> scrapers;

    public PriceTag() {
        scrapers = getScrapers();
    }

    public PriceTag(final List<Scraper> scrapers) {
        this.scrapers = scrapers;
    }

    protected Scraper getScraperFromUrl(String url) throws ScraperNotFound {
        return scrapers.stream().filter(s -> s.matches(url)).findAny()
                .orElseThrow(() -> new ScraperNotFound("Scraper not found for URL: " + url));
    }

    public Product process(final String url) throws ScraperNotFound, ParseException {
        return getScraperFromUrl(url).process(url);
    }

    private List<Scraper> getScrapers() {
        final Reflections reflections = new Reflections("com.github.lucasfsousa.pricetag.scraper");
        final Set<Class<? extends Scraper>> scraperClasses = reflections.getSubTypesOf(Scraper.class);
        final List<Scraper> scrapers = new ArrayList<>();
        for (final Class<? extends Scraper> c : scraperClasses) {
            try {
                scrapers.add(c.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("Error creating instance of: " + c.getName());
            }
        }
        return scrapers;
    }
}
