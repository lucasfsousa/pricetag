# pricetag
Get the price information about products in some stores, see supported stores below.

[![Build Status](https://travis-ci.org/lucasfsousa/pricetag.svg?branch=master)](https://travis-ci.org/lucasfsousa/pricetag)

## How it works
Using the library [JSOUP](https://jsoup.org/), is possible to read the page and select some data using css selectors.

## Example:

    public class App {
        public static void main(final String[] args) throws ScraperNotFound, ParseException {
            final PriceTag pricetag = new PriceTag();
            final Product product = pricetag
                    .process("https://www.amazon.de/Nintendo-2500066-Switch-Konsole-Grau/dp/B01M6ZGICT");
            System.out.println(product.getPriceAsText());
        }
    }

## Maven
```
<dependency>
  <groupId>com.github.lucasfsousa</groupId>
  <artifactId>pricetag</artifactId>
  <version>0.0.2</version>
</dependency>
```

## Supported stores:

### Internacional

 - Amazon (tested: com, br, uk, de)

### Brazil

 - Submarino
 - Americanas
 - Shoptime

### Germany

 - Saturn
 - Media Markt
 - Bücher.de

## TODOs
 - Add support to others stores (Gamestop \o/)
 - Improve documentation
