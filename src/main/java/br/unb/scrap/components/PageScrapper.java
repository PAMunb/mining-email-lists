package br.unb.scrap.components;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import br.unb.scrap.model.Post;

/**
 * 
 *
 */
@Component
public class PageScrapper extends ConnectionJsoup {

	/**
	 * @param url
	 * @return
	 */
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
	 * @return
	 */
	private List<String> getLinksByThread() {
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

	/**
	 * @param doc
	 * @return
	 */
	private Elements extractLiTags(Document doc) {
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
	public Set<String> getLinksMessages() {
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
		System.out.println(msgs.size());
		return msgs;
	}

	/**
	 * @return
	 */
	public List<Post> execute() {
		List<Post> posts = new LinkedList<>();
		Set<String> urls = getLinksMessages();
		for (String url : urls) {
			Post post = scrap(url);
			posts.add(post);
//			break;
		}
		System.out.println(posts);
		return posts;
	}

	/**
	 * Método que retorna o autor e a data do HTML presente em "body p"
	 * 
	 * @param doc  passando o doc como parâmentro
	 * @param post passando o post como parâmetro
	 */
	private void retrieveAuthorAndDate(Document doc, Post post) {
		Element element = doc.select("body p").first();
		String phrase = element.text();

		// Retira a tag EM
		element.select("em").remove();
		phrase = element.text();

		// Divide a frase em duas partes
		int index = phrase.length() / 2;
		String name = phrase.substring(0, index - 2);
		String date = phrase.substring(index - 1);

		// System.out.println("Autor: " + nome);
		// System.out.println("Date:" + data);

		post.setName(name);
		post.setDate(date);
	}

	/**
	 * Retorna o corpo do HTML presente em "title"
	 * 
	 * @param doc  passando o doc como parâmentro
	 * @param post passando o post como parâmetro
	 */
	private void retrieveTitle(Document doc, Post post) {
		Elements title = doc.select("title");
		for (Element t : title) {
			String text = t.text();
			// System.out.println("Assunto: " + texto);
			post.setTitle(text);
		}
	}

	/**
	 * Retorna o corpo do HTML presente na tag "p"
	 * 
	 * @param doc  passando o doc como parâmentro
	 * @param post passando o post como parâmetro
	 */
	private void retrieveBody(Document doc, Post post) {
		Elements paragraphs = doc.select("p");
		for (Element paragraph : paragraphs) {
			String text = paragraph.text().trim();
			if (!text.isEmpty()) {
				// System.out.println("corpo do e-mail");
				System.out.println(text);
				post.setBody(text);
			}
		}
	}

	/**
	 * 
	 * Busca o termo "reply" presente no HTML e retorna se é o e-mail original ou de
	 * resposta
	 * 
	 * @param doc  passando o doc como parâmentro
	 * @param post passando o post como parâmetro
	 */
	private void retrieveIsOriginal(Document doc, Post post) {
		String searchTerm = "Reply";
		String html = doc.html();
		if (html.contains(searchTerm)) {
			System.out.println("This is the original email");
			post.setOriginal("This is the original email");
		} else {
			System.out.println("This is the reply email");
			post.setOriginal("This is the reply email");
		}
	}

}
