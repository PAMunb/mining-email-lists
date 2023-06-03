package br.unb.scrap.components;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionJsoupTest {

	String url = ScrapBoost.BASE_URL;
	ConnectionJsoup cj = new ConnectionJsoup();

	@Test
	public void testJsoupConnection() {
		try {
			Document document = cj.connect(url);
			Assert.assertNotNull(document);
		} catch (IOException e) {
			Assert.fail("Falha na conexão: " + e.getMessage());
		}
	}
}