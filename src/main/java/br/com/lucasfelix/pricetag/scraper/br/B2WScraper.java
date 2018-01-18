package br.com.lucasfelix.pricetag.scraper.br;

import org.jsoup.nodes.Document;

import br.com.lucasfelix.pricetag.ParseException;
import br.com.lucasfelix.pricetag.scraper.Scraper;

public class B2WScraper extends Scraper {

    @Override
    protected String getTitle(final Document document) throws ParseException {
        return document.select(".product-name").text();
    }

    @Override
    protected String getPrice(final Document document) throws ParseException {
        return document.select(".main-price .sales-price").text().replaceAll("\\.", "");
    }

    @Override
    public boolean matches(final String url) {
        return url.matches("https?:\\/\\/(www\\.)?(americanas|submarino|shoptime)\\.com\\.br.*");
    }

}
