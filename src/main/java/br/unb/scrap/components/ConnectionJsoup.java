package br.unb.scrap.components;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Classe de configuração que utiliza o Jsoup, uma biblioteca java, que seve
 * para manipular dados em documentos HTML. É uma conexão necessária para o
 * scrapping da página.
 *
 */
public class ConnectionJsoup {

	/**
	 * Método que utiliza a biblioteca Jsoup para fazer uma conexão com uma página
	 * HTML.
	 *
	 * @param url a URL da página para conectar
	 * @return o HTML da página em formato de documento
	 * @throws IOException se ocorrer algum erro de I/O durante a conexão
	 */
	protected Document connect(String url) throws IOException {
		Connection connection = Jsoup.connect(url);
		return connection.get();
	}

}
