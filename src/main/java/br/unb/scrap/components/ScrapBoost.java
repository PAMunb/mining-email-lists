package br.unb.scrap.components;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import br.unb.scrap.enums.PostType;
import br.unb.scrap.model.Post;

@Component
public class ScrapBoost implements PageScraper {

	private static final Logger logger = LogManager.getLogger(ScrapBoost.class);
	public static final String BASE_URL = "https://lists.boost.org/Archives/boost/";
	private static final String DATE_TAG_SELECTOR = "tbody > tr > td:nth-child(3)";
	private static final String THREAD_TAG_SELECTOR = "tbody > tr > td:nth-child(4)";
	private static final String DATE_URL_SUFFIX = "date.php";
	private final FileLogger fileLogger;

	public ScrapBoost() {
		fileLogger = new FileLogger();
	}

	/**
	 * O método scrap(String url) é responsável por extrair informações relevantes
	 * de um determinado URL e criar um objeto Post.
	 */
	@Override
	public Post scrap(String url) {
		Post post = new Post();
		try {
			Document doc = Jsoup.connect(url).get();

			retrieveAuthorAndDate(doc, post);
			retrieveTitle(doc, post);
			retrieveBody(doc, post);
			retrievePostType(doc, post);

		} catch (Exception e) {
			logger.error("scrap", e);
			fileLogger.logException("Error while scrapping", "url", e);
		}
		return post;
	}

	/**
	 * método é responsável por obter os links dos posts por data a partir de um
	 * documento HTML, a partir da BASE_URL ou da url passada.
	 * 
	 * @return Retorna a lista urls contendo as URLs das threads extraídas.
	 */
	public List<String> getLinksByDate() throws IOException {
		List<String> dateUrls = new LinkedList<>();
		try {
			Document doc = Jsoup.connect(BASE_URL).get();
			Elements tables = doc.select("table");
			for (Element table : tables) {
				Elements dateColumns = table.select(DATE_TAG_SELECTOR);
				for (Element column : dateColumns) {
					Elements links = column.select("a");
					if (links.size() > 0) {
						Element link = links.first();
						if (link.text().endsWith("Date")) {
							String url = BASE_URL + link.attr("href");
							dateUrls.add(url);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error while getting links by date", e);
			fileLogger.logException("Error while getting links by date", "url", e);
		}
		return dateUrls;
	}

	/**
	 * método é responsável por obter os links das threads a partir de um documento
	 * HTML, a partir da BASE_URL ou da url passada.
	 * 
	 * @return Retorna a lista urls contendo as URLs das threads extraídas.
	 */
	public List<String> getLinksByThread() throws IOException {
		List<String> threadUrls = new LinkedList<>();
		try {
			Document doc = Jsoup.connect(BASE_URL).get();
			Elements tables = doc.select("table");
			for (Element table : tables) {
				Elements threadColumns = table.select(THREAD_TAG_SELECTOR);
				for (Element column : threadColumns) {
					Elements links = column.select("a");
					if (links.size() > 0) {
						Element link = links.first();
						if (link.text().endsWith("Thread")) {
							String url = BASE_URL + link.attr("href");
							threadUrls.add(url);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error while getting links by thread", e);
			fileLogger.logException("Error while getting links by thread", "url", e);
		}
		return threadUrls;
	}

	/**
	 * Método é responsável por extrair as tags li de um documento HTML.
	 * 
	 * @param doc passando o document como parâmentro
	 * @return Retorna a coleção liTags contendo todas as tags li extraídas.
	 */
	public Elements extractLiTags(Document doc) {
		Elements liTags = new Elements();
		try {
			Element ulParent = doc.select("ul").first();
			Elements lis = ulParent.select("li");
			for (Element li : lis) {
				liTags.add(li);
				/*
				 * Element ul = li.select("ul").first(); if (ul != null) { Document newDoc =
				 * Jsoup.parse(ul.toString()); // Chama recursivamente o método
				 * liTags.addAll(extractLiTags(newDoc)); } // break;
				 */
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

	public Set<String> getLinksMessages() throws IOException {
		Set<String> msgs = new HashSet<String>();
		List<String> urls = getLinksByDate();
		// List<String> urls = getLinksByThread();
		for (String url : urls) {
			try {
				Connection connection = Jsoup.connect(url);
				Document doc = connection.get();
				for (Element li : extractLiTags(doc)) {
					String link = li.select("a").attr("href");
					msgs.add(url.replace(DATE_URL_SUFFIX, link));
				}
//				break;
			} catch (Exception e) {
				logger.error("Error while getting links messages for URL: " + url, e);
				fileLogger.logException("Error while getting links messages for URL:", "url", e);
			}
		}
//		logger.info(msgs.size());
		return msgs;
	}

	/**
	 * Método que percorre uma lista de URLs, faz a raspagem de dados dessas URLs
	 * para criar objetos Post e armazena esses objetos em uma lista.
	 * 
	 * @return Retorna uma lista de objetos do tipo Post
	 */
	public List<Post> execute() throws IOException {
		List<Post> posts = new LinkedList<>();
		Set<String> urls;
		try {
			urls = getLinksMessages();
		} catch (IOException e) {
			e.printStackTrace();
			return posts;
		}
		for (String url : urls) {
			try {
				Post post = scrap(url);
				posts.add(post);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info(posts);
		return posts;
	}

	/**
	 * Método que recebe um objeto Document e um objeto Post como parâmetros. O
	 * objetivo desse método é extrair informações sobre o autor e a data de um
	 * documento HTML e atribuí-las ao objeto Post.
	 * 
	 * @param doc  passando o documento como parâmentro
	 * @param post passando o post como parâmetro
	 */
	public void retrieveAuthorAndDate(Document doc, Post post) {
		try {
			Element headers = doc.select("p.headers").first();
			String headersContent = headers.text();

			String name = "";
			String date = "";

			Pattern authorPattern = Pattern.compile("From:\\s(.+?)\\s\\(");
			Matcher authorMatcher = authorPattern.matcher(headersContent);
			if (authorMatcher.find()) {
				name = authorMatcher.group(1).trim();
			}

			Pattern datePattern = Pattern.compile("Date:\\s(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})");
			Matcher dateMatcher = datePattern.matcher(headersContent);
			if (dateMatcher.find()) {
				date = dateMatcher.group(1).trim();
			}

			logger.info("Autor: " + name);
			logger.info("Data: " + date);

			post.setAuthorName(utf8EncodedString(name));
			post.setPublicationDate(date);
		} catch (Exception e) {
			logger.error("Error while retrieving author and date", e);
			fileLogger.logException("Error while retrieving author and date", "url", e);
		}
	}

	/**
	 * Método que extrai o conteúdo do título do post a partir do documento HTML
	 * fornecido. Ele seleciona o elemento de título no documento, obtém o texto
	 * desse elemento e define esse texto como o título do post no objeto Post
	 * 
	 * @param doc  passando o documento como parâmentro
	 * @param post passando o post como parâmetro
	 */
	public void retrieveTitle(Document doc, Post post) {
		try {
			Elements title = doc.select("title");
			for (Element t : title) {
				String text = t.text();
				text = text.replace("Boost mailing page: ", ""); // remove título supérfluo
				logger.info("title: " + text);
				post.setTitle(utf8EncodedString(text));
			}
		} catch (Exception e) {
			logger.error("Error while retrieving subject", e);
			fileLogger.logException("Error while retrieving subject", "url", e);
		}

	}

	/**
	 * Método que extrai o conteúdo do corpo do post a partir do documento HTML
	 * fornecido. Ele obtém o texto entre as tags start e end do body. no documento,
	 * obtém o texto de cada parágrafo e define esse texto como o corpo do post no
	 * objeto Post
	 * 
	 * @param doc  passando o documento como parâmentro
	 * @param post passando o post como parâmetro
	 */
	public void retrieveBody(Document doc, Post post) {
		try {

			String start = "<!-- body=\"start\" -->";
			String end = "<!-- body=\"end\" -->";
			String tagBody = getStringBetweenTwoCharacters(doc.toString(), start, end);
			doc = Jsoup.parse(tagBody);

			Elements paragraphs = doc.select("p");
			List<String> paragraphTexts = paragraphs.stream().map(Element::text).map(String::trim)
					.filter(text -> !text.isEmpty()).collect(Collectors.toList());

			String body = String.join("\n", paragraphTexts);
			post.setBody(utf8EncodedString(body));
			// System.out.println(post.getBody());
		} catch (Exception e) {
			logger.error("Error while retrieving body", e);
			fileLogger.logException("Error while retrieving body", "url", e);
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
			fileLogger.logException("getStringBetweenTwoCharacters Exception", "url", e);
			return null;
		}
	}

	/**
	 * Este método recebe uma string (str) e realiza a codificação UTF-8 dessa
	 * string. Ele converte a string em uma sequência de bytes usando a codificação
	 * UTF-8 e, em seguida, cria uma nova string a partir desses bytes, também
	 * usando a codificação UTF-8.
	 * 
	 * @param str passando A string como parâmentro
	 * @return Retona a String codificada em UTF-8.
	 */
	protected String utf8EncodedString(String str) {
		try {
			byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
			return new String(bytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			fileLogger.logException("utf8EncodedString Exception", "url", e);
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
	 * @param doc  passando o documento como parâmentro
	 * @param post passando o post como parâmetro
	 */
	public boolean retrievePostType(Document doc, Post post) {
		System.out.println("*----------------------------------------------------------*");
		try {
			String searchTerm = "Reply";
			String html = doc.html();
			if (html.contains(searchTerm)) {
				logger.info("This is the original email" + " >>>>>>" + true);
				post.setPostType(PostType.ORIGINAL);
				return true;
			} else {
				logger.info("This is the reply email" + " >>>>>>" + false);
				post.setPostType(PostType.REPLY);
				return false;
			}
		} catch (Exception e) {
			logger.error("Error while retrieving post Type", e);
			fileLogger.logException("Error while retrieving post Type", "url", e);
			return false;
		}
	}

}