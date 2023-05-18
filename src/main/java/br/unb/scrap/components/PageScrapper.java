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
 * Classe com anotação @Component, contém os métodos que fazem o scrapping das
 * páginas HTML e estende a Classe de conexão com Jsoup
 *
 */
@Component
public class PageScrapper extends ConnectionJsoup {

	/**
	 * Método responsável por fazer a extração de informações de uma página web com
	 * base em uma URL fornecida
	 * 
	 * @param url Passando a url como parâmetro
	 * @return Retorna um objeto do tipo Post contendo informações.
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
	 * método é responsável por obter os links das threads a partir de um documento
	 * HTML, a partir da BASE_URL ou da url passada.
	 * 
	 * @return Retorna a lista urls contendo as URLs das threads extraídas.
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
	 * Método é responsável por extrair as tags
	 * <li>de um documento HTML.
	 * 
	 * @param doc passando o document como parâmentro
	 * @return Retorna a coleção liTags contendo todas as tags
	 *         <li>extraídas.
	 */
	private Elements extractLiTags(Document doc) {
		Elements liTags = new Elements();
		Element ulParent = doc.select("ul").first();
		Elements lis = ulParent.select("li");
		for (Element li : lis) {
			liTags.add(li);
			Element ul = li.select("ul").first();
			if (ul != null) {
				Document newDoc = Jsoup.parse(ul.toString());
				// Chama recursivamente o método
				liTags.addAll(extractLiTags(newDoc));
			}
		}
		return liTags;
	}

	/**
	 * Método é responsável por obter os links das mensagens a partir das URLs
	 * das threads
	 * 
	 * @return Retorna um conjunto msgs contendo os links das mensagens extraídas.
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
	 * Método que percorre uma lista de URLs, faz a raspagem de dados dessas URLs
	 * para criar objetos Post e armazena esses objetos em uma lista.
	 * 
	 * @return Retorna uma lista de objetos do tipoo Post
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
	 * Método que extrai o autor e a data do post a partir do documento HTML
	 * fornecido. Ele seleciona o primeiro elemento de parágrafo dentro do elemento
	 * <body p>, remove as tags <em> desse elemento e divide o texto resultante em
	 * duas partes para obter o nome do autor e a data do post
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
	 * Método que extrai o conteúdo do título do post a partir do documento HTML
	 * fornecido. Ele seleciona o elemento de título no documento, obtém o texto
	 * desse elemento e define esse texto como o título do post no objeto Post
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
	 * Método que extrai o conteúdo do corpo do post a partir do documento HTML
	 * fornecido. Ele percorre todos os elementos de parágrafo
	 * <p>
	 * no documento, obtém o texto de cada parágrafo e define esse texto como o
	 * corpo do post no objeto Post
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
	 * Método que verifica se a palavra-chave ("Reply") está presente no conteúdo
	 * HTML de um documento. Se for encontrada, define se o post é um email original
	 * e se não for encontrada, define que é um email de resposta
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
