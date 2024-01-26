package br.unb.scrap.components;

import java.io.IOException;
import java.util.stream.Stream;

import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import br.unb.scrap.utils.UrlUtils;

public class ConnectionJsoupTest {

	String urlOpenJdk = UrlUtils.BASE_URL_OPENJDK;
	String urlBoost = UrlUtils.BASE_URL_BOOST;

	private ConnectionJsoup cj = new ConnectionJsoup();

	@ParameterizedTest
	@MethodSource("provideUrls")
	public void testJsoupConnection(String url) {
		try {
			Document document = cj.connect(url);
			Assert.assertNotNull(document);
		} catch (IOException e) {
			Assert.fail("Falha na conexão: " + e.getMessage());
		}
	}

	private static Stream<String> provideUrls() {
		return Stream.of(UrlUtils.BASE_URL_OPENJDK, UrlUtils.BASE_URL_BOOST);
	}
}