package br.unb.scrap.config;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import lombok.Data;

/**
 * Classe de configuração que utiliza o Jsoup para fazer conexões com páginas
 * HTML.
 */
@Data
public class ConnectionJsoup {

	/**
	 * Conecta-se a uma página HTML especificada pela URL e retorna o documento
	 * HTML.
	 *
	 * @param url a URL da página para conectar
	 * @return o HTML da página em formato de documento
	 * @throws IOException se ocorrer algum erro de I/O durante a conexão
	 */
	public static Document connect(String url) throws IOException {
		try {
			Connection connection = Jsoup.connect(url);
			return connection.get();
		} catch (IOException e) {
			throw new IOException("Erro ao conectar-se à URL: " + url, e);
		}
	}

}
