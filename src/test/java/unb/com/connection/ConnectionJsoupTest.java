package unb.com.connection;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionJsoupTest {

	ConnectionJsoup cj = new ConnectionJsoup();

	@Test
	public void testJsoupConnection() {
		try {
			String url = ConnectionJsoup.BASE_URL;
			Document document = Jsoup.connect(url).get();
			assertNotNull("Jsoup connection failed", document);
		} catch (IOException e) {
			Assert.fail("Error connecting to website: " + e.getMessage());
		}
	}
}