package unb.com.scrapymailists;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
		// new PageScrapper().execute().forEach(System.out::println);
		execute();
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

	private static List<String> getLinksByThread() {
		Document doc = connect(BASE_URL);
		Elements tables = doc.select("table");
		List<String> urls = new LinkedList<>();
		for (Element table : tables) {
			Elements tdTags = table.select("tbody > tr > td:nth-child(4)");
			for (Element tag : tdTags) {
				if (tag.select("a").text().toString().endsWith("Thread")) {
					urls.add(BASE_URL + tag.select("a").attr("href"));
				}
			}
		}
		return urls;
	}

	private static Set<String> getLinksMessages() {
		Set<String> msgs = new HashSet<String>();
		List<String> urls = getLinksByThread();
		for (String url : urls) {
			Document doc = connect(url);
			for (Element li : extractLiTags(doc)) {
				String link = li.select("a").attr("href");
				msgs.add(url.replace("index.php", link));
			}
			break;
		}
		// msgs.forEach(t -> System.out.println(t));
		System.out.println(msgs.size());
		return msgs;
	}

	public static Elements extractLiTags(Document doc) {
		Elements liTags = new Elements();
		Element ulParent = doc.select("ul").first();
		Elements lis = ulParent.select("li");
		for (Element li : lis) {
			liTags.add(li);
			Element ul = li.select("ul").first();
			if (ul != null) {
				// System.out.println(ul.toString());
				Document newDoc = Jsoup.parse(ul.toString());
				liTags.addAll(extractLiTags(newDoc));
			}
		}
		return liTags;
	}

	/**
	 * @return
	 */
	public static List<Post> execute() {
		List<Post> posts = new LinkedList<>();
		Set<String> urls = getLinksMessages();
		for (String url : urls) {
			Post post = scrap(url);
			posts.add(post);
			break;
		}
		System.out.println(posts);
		return posts;
	}

	private static Post scrap(String url) {
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
	private static void retrieveAuthorAndDate(Document doc, Post post) {
		Element element = doc.select("body p").first();
		String frase = element.text();

		// Retira a tag EM
		element.select("em").remove();
		frase = element.text();

		// Divide a frase em duas partes
		int index = frase.length() / 2;
		String nome = frase.substring(0, index - 2);
		String data = frase.substring(index - 1);

		// System.out.println("Autor: " + nome);
		// System.out.println("Date:" + data);

		post.setName(nome);
		post.setDate(data);
	}

	/**
	 * Retorna o corpo do HTML presente em "title"
	 * 
	 * @param passando o doc como parâmentro
	 * @param passando o post como parâmetro
	 */
	private static void retrieveTitle(Document doc, Post post) {
		Elements title = doc.select("title");
		for (Element t : title) {
			String texto = t.text();
			// System.out.println("Assunto: " + texto);
			post.setTitle(texto);
		}
	}

	/**
	 * Retorna o corpo do HTML presente na tag "p"
	 * 
	 * @param passando o doc como parâmentro
	 * @param passando o post como parâmetro
	 */
	private static void retrieveBody(Document doc, Post post) {
		Elements paragrafos = doc.select("p");
		for (Element paragrafo : paragrafos) {
			String texto = paragrafo.text().trim();
			if (!texto.isEmpty()) {
				// System.out.println("corpo do e-mail");
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
	private static void retrieveIsOriginal(Document doc, Post post) {
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
