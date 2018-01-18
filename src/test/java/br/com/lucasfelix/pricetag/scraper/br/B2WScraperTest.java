package br.com.lucasfelix.pricetag.scraper.br;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jsoup.nodes.Document;
import org.junit.Test;

import br.com.lucasfelix.pricetag.AbstractScraperTest;

public class B2WScraperTest extends AbstractScraperTest {
    private final B2WScraper scraper = new B2WScraper();

    @Test
    public void shouldMatchB2WUrls() {
        assertTrue(scraper.matches(
                "https://www.submarino.com.br/produto/129361190/microsoft-office-365-home-5-licencas-pc-mac-android-e-ios-1-tb-de-hd-virtual-para-cada-licenca?pfm_carac=office&pfm_index=0&pfm_page=search&pfm_pos=grid&pfm_type=search_page%20"));
        assertTrue(scraper.matches(
                "https://www.americanas.com.br/produto/131471591?chave=prf_hm_0_oh_2_txar_00&pfm_carac=&pfm_index=1&pfm_page=home&pfm_pos=maintop3&pfm_type=vit_spacey"));
        assertTrue(scraper.matches(
                "https://www.shoptime.com.br/produto/125701169/panela-eletronica-de-pressao-fun-kitchen-inox-4l-fun-kitchen-com-2-anos-garantia?chave=prf_hm_oh_0_2_0&pfm_carac=Nome%20da%20area&pfm_index=1&pfm_page=home&pfm_pos=maintop3&pfm_type=vit_spacey"));
        // SouBarato is from B2W, but is not supported
        assertFalse(scraper.matches(
                "http://www.soubarato.com.br/produto/127695161/usado-camera-digital-gopro-hero-plus-8.1mp-com-wifi-bluetooth-e-gravacao-full-hd-preta?chave=prf_hm_ct_0_2_central02"));
    }

    @Test
    public void shouldParseAmericanasProduct() throws Exception {
        final Document document = getDocument("br/americanas");
        assertEquals("R$ 338,16", scraper.getPrice(document));
        assertEquals("HD Externo Portátil Seagate Expansion 2TB USB 3.0 Preto", scraper.getTitle(document));
    }

    @Test
    public void shouldParseSubmarinoProduct() throws Exception {
        final Document document = getDocument("br/submarino");
        assertEquals("R$ 3399,00", scraper.getPrice(document));
        assertEquals(
                "Smartphone Samsung Galaxy S8 Dual Chip Android 7.0 Tela 5.8\" Octa-Core 2.3GHz 64GB 4G Câmera 12MP - Preto",
                scraper.getTitle(document));
    }

    @Test
    public void shouldParseShoptimeProduct() throws Exception {
        final Document document = getDocument("br/shoptime");
        assertEquals("R$ 199,99", scraper.getPrice(document));
        assertEquals("Jogo de Panelas Tramontina Paris 7 Peças Cereja Alumínio", scraper.getTitle(document));
    }
}
