package com.github.lucasfsousa.pricetag;

import java.math.BigDecimal;

public class Product {
    private String url;
    private String title;
    private final String priceAsText;
    private BigDecimal price;

    public Product(final String url, final String title, final String priceAsText, final BigDecimal price) {
        this.url = url;
        this.title = title;
        this.priceAsText = priceAsText;
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public String getPriceAsText() {
        return priceAsText;
    }

    @Override
    public String toString() {
        return "Product [url=" + url + ", title=" + title + ", priceAsText=" + priceAsText + ", price=" + price + "]";
    }

}
