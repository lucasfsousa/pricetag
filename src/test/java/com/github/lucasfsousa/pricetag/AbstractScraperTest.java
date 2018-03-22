package com.github.lucasfsousa.pricetag;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class AbstractScraperTest {
    public Document getDocument(final String resource, final String url) throws IOException {
        final InputStream is = this.getClass().getClassLoader().getResourceAsStream(resource);
        return Jsoup.parse(is, "UTF-8", url);
    }

    public Document getDocument(final String resource) throws IOException {
        return getDocument(resource, "http://some.url");
    }
}
