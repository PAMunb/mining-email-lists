package br.unb.scrap.components;

import java.nio.charset.StandardCharsets;
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
 * Classe com métodos para scrap da página Boost (URL passada)
 *
 */
@Component
public class ScrapBoost extends PageScrapper {
	/**
	 * O método scrap(String url) é responsável por extrair informações relevantes
	 * de um determinado URL e criar um objeto Post.
	 */
	@Override
	protected Post scrap(String url) {
		Post post = new Post();
		try {
			Document doc = connect(url);

			retrieveAuthorAndDate(doc, post);
			retrieveTitle(doc, post);
			retrieveBody(doc, post);

			retrieveIsOriginal(doc, post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return post;
	}

	/**
	 * método é responsável por obter os links dos posts por data a partir de um documento
	 * HTML, a partir da BASE_URL ou da url passada.
	 * 
	 * @return Retorna a lista urls contendo as URLs das threads extraídas.
	 */
	protected List<String> getLinksByDate() {
		List<String> urls = new LinkedList<>();
		try {
			Document doc = connect(BASE_URL);
			Elements tables = doc.select("table");
			for (Element table : tables) {
				Elements tdTags = table.select("tbody > tr > td:nth-child(3)");
				for (Element tag : tdTags) {
					if (tag.select("a").text().toString().endsWith("Date")) {
						urls.add(BASE_URL + tag.select("a").attr("href"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(urls);
		return urls;
	}

	/**
	 * método é responsável por obter os links das threads a partir de um documento
	 * HTML, a partir da BASE_URL ou da url passada.
	 * 
	 * @return Retorna a lista urls contendo as URLs das threads extraídas.
	 */
	protected List<String> getLinksByThread() {
		List<String> urls = new LinkedList<>();
		try {
			Document doc = connect(BASE_URL);
			Elements tables = doc.select("table");
			for (Element table : tables) {
				Elements tdTags = table.select("tbody > tr > td:nth-child(4)");
				for (Element tag : tdTags) {
					if (tag.select("a").text().toString().endsWith("Thread")) {
						urls.add(BASE_URL + tag.select("a").attr("href"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println(urls);
		return urls;
	}

	/**
	 * Método é responsável por extrair as tags li de um documento HTML.
	 * 
	 * @param doc passando o document como parâmentro
	 * @return Retorna a coleção liTags contendo todas as tags li extraídas.
	 */
	protected Elements extractLiTags(Document doc) {
		Elements liTags = new Elements();
		try {
			Element ulParent = doc.select("ul").first();
			Elements lis = ulParent.select("li");
			for (Element li : lis) {
				liTags.add(li);
				/* Element ul = li.select("ul").first();
				if (ul != null) {
					Document newDoc = Jsoup.parse(ul.toString());
					// Chama recursivamente o método
					liTags.addAll(extractLiTags(newDoc));
				}
//	            break; */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return liTags;
	}

	/**
	 * Método é responsável por obter os links das mensagens a partir das URLs das
	 * threads
	 * 
	 * @return Retorna um conjunto msgs contendo os links das mensagens extraídas.
	 */

	public Set<String> getLinksMessages() {
		Set<String> msgs = new HashSet<String>();
		//List<String> urls = getLinksByThread();
		List<String> urls = getLinksByDate();
		for (String url : urls) {
			try {
				Document doc = connect(url);
				for (Element li : extractLiTags(doc)) {
					String link = li.select("a").attr("href");
					msgs.add(url.replace("date.php", link));
				}
	            break;
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			try {
				Post post = scrap(url);
				posts.add(post);
//	            break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(posts);
		return posts;
	}

	/**
	 * Método que extrai o autor e a data do post a partir do documento HTML
	 * fornecido. Ele seleciona o primeiro elemento de parágrafo dentro do elemento
	 * body p, remove as tags em desse elemento e divide o texto resultante em duas
	 * partes para obter o nome do autor e a data do post
	 * 
	 * @param doc  passando o doc como parâmentro
	 * @param post passando o post como parâmetro
	 */
	protected void retrieveAuthorAndDate(Document doc, Post post) {
		try {
			Element element = doc.select("body p").first();
			String phrase = element.text();

			// Retira a tag EM
			element.select("em").remove();
			phrase = element.text();

			// Divide a frase em nome e data
			String name = phrase.substring(0, phrase.indexOf("(") - 1);
			String date = phrase.substring(phrase.indexOf("Date:"));

			System.out.println("Autor: " + name);
			System.out.println("Date:" + date);

			post.setName(utf8EncodedString(name));
			post.setDate(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que extrai o conteúdo do título do post a partir do documento HTML
	 * fornecido. Ele seleciona o elemento de título no documento, obtém o texto
	 * desse elemento e define esse texto como o título do post no objeto Post
	 * 
	 * @param doc  passando o doc como parâmentro
	 * @param post passando o post como parâmetro
	 */
	protected void retrieveTitle(Document doc, Post post) {
		try {
			Elements title = doc.select("title");
			for (Element t : title) {
				String text = t.text();
				text = text.replace("Boost mailing page: ", "");	// remove título supérfluo
				System.out.println("Assunto: " + text);
				post.setTitle(utf8EncodedString(text));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que extrai o conteúdo do corpo do post a partir do documento HTML
	 * fornecido. Ele obtém o texto entre as tags start e end do body. no documento,
	 * obtém o texto de cada parágrafo e define esse texto como o corpo do post no
	 * objeto Post
	 * 
	 * @param doc  passando o doc como parâmentro
	 * @param post passando o post como parâmetro
	 */
	protected void retrieveBody(Document doc, Post post) {
		try {
			String tagBody = getStringBetweenTwoCharacters(doc.toString(), "<!-- body=\"start\" -->",
					"<!-- body=\"end\" -->");
			doc = Jsoup.parse(tagBody);
			String body = "";
			Elements paragraphs = doc.select("p");
			for (Element paragraph : paragraphs) {
				String text = paragraph.text().trim();
				if (!text.isEmpty()) {
					// System.out.println("corpo do e-mail");
					body += text + "\n";
				}
			}
			post.setBody(utf8EncodedString(body));
			//System.out.println(post.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que extrai a substring que está entre esses dois caracteres. O método
	 * utiliza os métodos indexOf() e lastIndexOf() para obter as posições desses
	 * caracteres na string de entrada e, em seguida, utiliza o método substring()
	 * para retornar a substring correspondente.
	 * 
	 * @param input O input
	 * @param from  O from
	 * @param to    O to
	 * @return Retorna a substring
	 */
	protected String getStringBetweenTwoCharacters(String input, String from, String to) {
		try {
			return input.substring(input.indexOf(from) + 1, input.lastIndexOf(to));
		} catch (Exception e) {
			e.printStackTrace();
			return null; // Or any other appropriate action for error handling
		}
	}

	/**
	 * Este método recebe uma string (str) e realiza a codificação UTF-8 dessa
	 * string. Ele converte a string em uma sequência de bytes usando a codificação
	 * UTF-8 e, em seguida, cria uma nova string a partir desses bytes, também
	 * usando a codificação UTF-8.
	 * 
	 * @param str  passando A string como parâmentro
	 * @return Retona a String codificada em UTF-8.
	 */
	protected String utf8EncodedString(String str) {
		try {
			byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
			return new String(bytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

	/**
	 * 
	 * Método booleano que verifica se a palavra-chave ("Reply") está presente no
	 * conteúdo HTML de um documento. Se for encontrada, define se o post é um email
	 * original (true) e se não for encontrada, define que é um email de resposta
	 * (false).
	 * 
	 * @param doc  passando o doc como parâmentro
	 * @param post passando o post como parâmetro
	 */
	protected boolean retrieveIsOriginal(Document doc, Post post) {
		try {
			String searchTerm = "Reply";
			String html = doc.html();
			if (html.contains(searchTerm)) {
				System.out.println("This is the original email" + " >>>>>>" + true);
				post.setOriginal(true);
				return true;
			} else {
				System.out.println("This is the reply email" + " >>>>>>" + false);
				post.setOriginal(false);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}