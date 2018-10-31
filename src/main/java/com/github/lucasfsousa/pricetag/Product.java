package com.github.lucasfsousa.pricetag;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Product {
    private String store;
    private String countryCode;
    private String brand;
    private String url;
    private String title;
    private String priceAsText;
    private BigDecimal price;
    private List<String> images;
    private Map<String, String> metadata;

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriceAsText() {
        return priceAsText;
    }

    public void setPriceAsText(String priceAsText) {
        this.priceAsText = priceAsText;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

}
