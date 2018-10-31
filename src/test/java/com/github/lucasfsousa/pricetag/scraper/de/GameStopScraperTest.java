package com.github.lucasfsousa.pricetag.scraper.de;

import com.github.lucasfsousa.pricetag.AbstractScraperTest;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameStopScraperTest extends AbstractScraperTest {
    private final GameStopDeScraper scraper = new GameStopDeScraper();

    @Test
    public void shouldParseGameStopUrl() {
        assertTrue(scraper.matches(
                "https://www.gamestop.de/Switch/Games/52274/nintendo-switch-konsole-inkl-pok-mon-lets-go-pikachu-pok-ball"));
    }

    @Test
    public void shouldParseGameStopProduct() throws Exception {
        final Document document = getDocument("de/gamestop");

        assertEquals("69,99 €", scraper.getPrice(document));
        assertEquals("Nintendo Labo Fahrzeug-Set (Toy-Con 03)",
                scraper.getTitle(document));
        assertEquals("GameStop DE", scraper.getStore(document));
        assertEquals("DE", scraper.getCountryCode(document));
        assertEquals("Nintendo", scraper.getBrand(document));
        assertEquals(5, scraper.getImages(document).size());
        assertEquals("Neu: 280916 Gebraucht: 280917", scraper.getMetadata(document).get("Produktnummer"));
    }
}
