package unb.com.connection;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração que utiliza o Jsoup, uma biblioteca java, que seve
 * para manipular dados em documentos HTML. É uma conexão necessária para o
 * scrapping da página.
 *
 */
@Configuration
public class ConnectionJsoup {

	protected static final String BASE_URL = "https://lists.boost.org/Archives/boost/";
	/**
	 * Método que utiliza a biblioteca Jsoup para fazer uma conexão com
	 * uma página HTML
	 * 
	 * @param url passando a url como parâmetro
	 * @return retorna o HTML da página em formato de documento
	 */
	protected static Document connect(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return doc;
	}

}
