package unb.com.scrapymailists;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import unb.com.entities.Post;

public class PageScrapper {
	public static void main(String[] args) {
		String url = "https://lists.boost.org/Archives/boost//2022/07/253297.php";
		Post post = new PageScrapper().scrap(url);
		System.out.println("Iniciando webscrapping java....");
		System.out.println(post);
	}

	/**
	 * @param passando a String url como paramentro
	 * @return Retorna Autor, Data, Titulo, Corpo e se original
	 */
	public Post scrap(String url) {
		Post post = new Post();
		Document doc = connect(url);

		retrieveAuthorAndDate(doc, post);
		retrieveTitle(doc, post);
		retrieveBody(doc, post);

		retrieveIsOriginal(doc, post);

		return post;
	}

	/**
	 * Retorna o autor e a data do HTML presente na em "body p"
	 * 
	 * @param passando o doc como parâmentro
	 * @param passando o post como parâmetro
	 */
	private void retrieveAuthorAndDate(Document doc, Post post) {
		Element element = doc.select("body p").first();
		String frase = element.text();

		// Retira a tag EM
		element.select("em").remove();
		frase = element.text();

		// Divide a frase em duas partes
		int index = frase.length() / 2;
		String nome = frase.substring(0, index - 2);
		String data = frase.substring(index - 1);

//		System.out.println("Autor: " + nome);
//		System.out.println("Date:" + data);

		post.setName(nome);
		post.setDate(data);
	}

	/**
	 * Retorna o corpo do HTML presente em "title"
	 * 
	 * @param passando o doc como parâmentro
	 * @param passando o post como parâmetro
	 */
	private void retrieveTitle(Document doc, Post post) {
		Elements title = doc.select("title");
		for (Element t : title) {
			String texto = t.text();
//			System.out.println("Assunto: " + texto);
			post.setTitle(texto);
		}
	}

	/**
	 * Retorna o corpo do HTML presente na tag "p"
	 * 
	 * @param passando o doc como parâmentro
	 * @param passando o post como parâmetro
	 */
	private void retrieveBody(Document doc, Post post) {
		Elements paragrafos = doc.select("p");
		for (Element paragrafo : paragrafos) {
			String texto = paragrafo.text().trim();
			if (!texto.isEmpty()) {
//				System.out.println("corpo do e-mail");
				System.out.println(texto);
				post.setCorpo(texto);
			}
		}
	}

	/**
	 * 
	 * Busca o termo "reply" presente no HTML e retorna se é o e-mail original ou de
	 * resposta
	 * 
	 * @param passando o doc como parâmentro
	 * @param passando o post como parâmetro
	 */
	private void retrieveIsOriginal(Document doc, Post post) {
		String termoBuscado = "Reply";
		String html = doc.html();
		if (html.contains(termoBuscado)) {
			System.out.println("Esse é o e-mail original");
			post.setOriginal("Esse é o e-mail original");
		} else {
			System.out.println("Esse é o e-mail de resposta");
			post.setOriginal("Esse é o e-mail de resposta");
		}
	}

	/**
	 * Método para criar conexão com o html utilizando a biblioteca Jsoup
	 * 
	 * @param passando a String url como paramentro
	 * @return Retorna o Documento
	 */
	private Document connect(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return doc;
	}
}
