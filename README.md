# pricetag
Get the price information about products in some stores, see supported stores below.

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

## TODOs

 - Configure Travis CI
 - Add the package in the Maven Central Repository
 - Add support to others stores (Gamestop \o/)
 - Improve documentation
