package unb.com.scrapymailists;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import unb.com.entities.Post;

/**
 * Classe
 *
 */
public class PageScrapper {
	private static final String BASE_URL = "https://lists.boost.org/Archives/boost/";

	public static void main(String[] args) {
//		new PageScrapper().execute().forEach(System.out::println);
		Teste();
	}

	/**
	 * @param url
	 * @return
	 */
	private static Document connect(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return doc;
	}

	private static List<String> Teste() {
		Document doc = connect(BASE_URL);
		Elements tables = doc.select("table");
		List<String> urls = new LinkedList<>();
		for (Element table : tables) {
//			System.out.println(table);
			Elements tdTags = table.select("tbody > tr > td:nth-child(4)");
//			System.out.println(tdTags);
			for (Element tag : tdTags) {
//				System.out.println(tag.select("a").text());
				if (tag.select("a").text().toString().endsWith("Thread")) {
					urls.add(BASE_URL + tag.select("a").attr("href"));

				}
			}

		}
//		System.out.println(urls);
		return urls;

	}

	/**
	 * @return
	 */
	public List<Post> execute() {
		List<Post> posts = new LinkedList();
		List<String> urls = Teste();
			for (String url : urls) {
				Post post = scrap(url);
//				posts.add(url);
			}
			return posts;
		}

	private Post scrap(String url) {
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
}


//private List<String> listPosts(String url) {
//List<String> posts = new LinkedList();
//
//Document doc = connect(url);
//
//posts.add("254599.php");
//posts.add("254615.php");
//posts.add("254616.php");
//posts.add("254618.php");
//posts.add("254619.php");
//posts.add("254620.php");
//posts.add("254624.php");
//posts.add("254614.php");
//posts.add("254622.php");
//posts.add("254623.php");
//posts.add("254613.php");
//posts.add("254621.php");
//posts.add("254612.php");
//posts.add("254611.php");
//
//posts.add("254599.php");
//posts.add("254600.php");
//posts.add("254601.php");
//posts.add("254606.php");
//posts.add("254607.php");
//
//return posts;
//}
//
///**
//* @return
//*/
//private List<String> listByDay() {
//List<String> days = new LinkedList<>();
//Document doc = connect(BASE_URL);
//
//// dummy
//days.add("2023/05/");
//days.add("2023/04/");
//
//return days;
//}
