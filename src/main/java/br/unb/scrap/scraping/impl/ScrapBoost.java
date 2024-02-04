package br.unb.scrap.scraping.impl;

import static br.unb.scrap.utils.UrlUtils.BOOST_ARCHIVES_BASE_URL;

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import br.unb.scrap.domain.Post;
import br.unb.scrap.enums.PostTypeEnum;
import br.unb.scrap.logging.FileLogger;
import br.unb.scrap.scraping.PageScraper;
import lombok.Data;

/**
 * Classe responsável por realizar a raspagem de dados do site Boost.
 */
@Component
//@Primary
@Data
public class ScrapBoost implements PageScraper {

	private static final Logger logger = LogManager.getLogger(ScrapBoost.class);
	private static final String DATE_TAG_SELECTOR = "tbody > tr > td:nth-child(3)";
	private static final String THREAD_TAG_SELECTOR = "tbody > tr > td:nth-child(4)";
	private static final String DATE_URL_SUFFIX = "date.php";
	private final FileLogger fileLogger;

	public ScrapBoost() throws IOException {
		this.fileLogger = new FileLogger("scrap_log_Boost.txt");
	}
	
	/**
	 * Método auxiliar para lidar com exceções, logando e registrando-as.
	 */
	private void handleException(String message, Exception e, String url) {
		logger.error(message, e);
		fileLogger.logException(message, url, e);
	}

	/**
	 * Método para realizar a raspagem de dados de uma postagem.
	 */
	public Post scrap(String url) {
		Post post = new Post();
		try {
			Document doc = Jsoup.connect(url).get();

			retrieveAuthorAndDate(doc, post);
			retrieveTitle(doc, post);
			retrieveBody(doc, post);
			retrievePostType(doc, post);

		} catch (IOException e) {
			handleException("Error while scrapping", e, url);
		}
		return post;
	}

	/**
	 * Método para obter os links das postagens por data.
	 */
	public List<String> getLinksByDate() {
		List<String> dateUrls = new LinkedList<>();
		try {
			Document doc = Jsoup.connect(BOOST_ARCHIVES_BASE_URL).get();

			if (doc != null) {
				Elements tables = doc.select("table");
				for (Element table : tables) {
					Elements dateColumns = table.select(DATE_TAG_SELECTOR);
					for (Element column : dateColumns) {
						Elements links = column.select("a");
						if (links.size() > 0) {
							Element link = links.first();
							if (link.text().endsWith("Date")) {
								String url = BOOST_ARCHIVES_BASE_URL + link.attr("href");
								dateUrls.add(url);
							}
						}
					}
				}
			} else {
				logger.error("Document is null for BOOST_ARCHIVES_BASE_URL");
				fileLogger.logException("Document is null", BOOST_ARCHIVES_BASE_URL,
						new NullPointerException("Document is null"));
			}

		} catch (IOException e) {
			handleException("Error while getting links by date", e, BOOST_ARCHIVES_BASE_URL);
		}
		return dateUrls;
	}

	/**
	 * Método para obter os links das mensagens.
	 */
	public Set<String> getLinksMessages() {
		Set<String> msgs = new HashSet<>();
		List<String> urls = getLinksByDate();
		for (String url : urls) {
			try {
				Document doc = Jsoup.connect(url).get();
				for (Element li : extractLiTags(doc)) {
					String link = li.select("a").attr("href");
					msgs.add(url.replace(DATE_URL_SUFFIX, link));
				}
				//break; // debug
			} catch (IOException e) {
				handleException("Error while getting links messages for URL: " + url, e, url);
			}
		}
		logger.info(msgs.size()); // debug
		return msgs;
	}

	/**
	 * Método para obter os links das postagens por Thread. Não utilizada, por isso
	 * está marcada como @deprecated
	 */
	@Deprecated
	public List<String> getLinksByThread() throws IOException {
		List<String> threadUrls = new LinkedList<>();
		try {
			Document doc = Jsoup.connect(BOOST_ARCHIVES_BASE_URL).get();
			Elements tables = doc.select("table");
			for (Element table : tables) {
				Elements threadColumns = table.select(THREAD_TAG_SELECTOR);
				for (Element column : threadColumns) {
					Elements links = column.select("a");
					if (links.size() > 0) {
						Element link = links.first();
						if (link.text().endsWith("Thread")) {
							String url = BOOST_ARCHIVES_BASE_URL + link.attr("href");
							threadUrls.add(url);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error while getting links by thread", e);
			fileLogger.logException("Error while getting links by thread", BOOST_ARCHIVES_BASE_URL, e);
		}
		return threadUrls;
	}

	/**
	 * Método para executar a raspagem de dados.
	 */
	public List<Post> execute(String baseUrl) {
		List<Post> posts = new LinkedList<>();
		try {
			Set<String> urls = getLinksMessages();
			for (String url : urls) {
				try {
					Post post = scrap(url);
					posts.add(post);
				} catch (Exception e) {
					logger.error("Error while executing", e);
				}
			}
			logger.info(posts);
		} catch (Exception e) {
			logger.error("Error while executing", e);
		}
		return posts;
	}

	/**
	 * Método para recuperar autor e data da postagem.
	 */
	public void retrieveAuthorAndDate(Document doc, Post post) {
		try {
			Element headers = doc.select("p.headers").first();
			String headersContent = headers.text();

			String name = findPattern(headersContent, "From:\\s(.+?)\\s\\(");
			String date = findPattern(headersContent, "Date:\\s(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})");

			logger.info("Author: " + name);
			logger.info("Date: " + date);

			post.setAuthorName(utf8EncodedString(name));
			post.setPublicationDate(date);
		} catch (Exception e) {
			handleException("Error while retrieving author and date", e, "");
		}
	}

	/**
	 * Método para recuperar o título da postagem.
	 */
	public void retrieveTitle(Document doc, Post post) {
		try {
			Elements title = doc.select("title");
			for (Element t : title) {
				String text = t.text().replace("Boost mailing page: ", "");
				logger.info("Title: " + text);
				post.setTitle(utf8EncodedString(text));
			}
		} catch (Exception e) {
			handleException("Error while retrieving subject", e, "");
		}

	}

	/**
	 * Método para recuperar o corpo da postagem.
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
		} catch (Exception e) {
			handleException("Error while retrieving body", e, "");
		}
	}

	/**
	 * Método para recuperar o tipo de postagem (original/reply).
	 */
	public boolean retrievePostType(Document doc, Post post) {
		try {
			boolean isOriginal = doc.html().contains("Reply");
			logger.info("This is the " + (isOriginal ? "original" : "reply") + " email >>>>>> " + isOriginal);
			post.setPostType(isOriginal ? PostTypeEnum.ORIGINAL : PostTypeEnum.REPLY);
			return isOriginal;
		} catch (Exception e) {
			handleException("Error while retrieving post type", e, "");
			return false;
		}
	}

	/**
	 * Método para extrair as tags li de um documento HTML.
	 */
	public Elements extractLiTags(Document doc) {
		Elements liTags = new Elements();
		try {
			Element ulParent = doc.select("ul").first();
			Elements lis = ulParent.select("li");
			for (Element li : lis) {
				liTags.add(li);
			}
		} catch (Exception e) {
			logger.error("Error while extracting li tags", e);
			fileLogger.logException("Error while extracting li tags", "", e);
		}
		return liTags;
	}

	/**
	 * Método auxiliar para encontrar um padrão em uma string.
	 */
	private String findPattern(String input, String pattern) {
		Matcher matcher = Pattern.compile(pattern).matcher(input);
		return matcher.find() ? matcher.group(1).trim() : "";
	}

	/**
	 * Método auxiliar para obter uma substring entre dois caracteres em uma string.
	 */
	private String getStringBetweenTwoCharacters(String input, String from, String to) {
		try {
			return input.substring(input.indexOf(from) + 1, input.lastIndexOf(to));
		} catch (Exception e) {
			handleException("Error while getting string between two characters", e, "");
			return null;
		}
	}

	/**
	 * Método auxiliar para codificar uma string em UTF-8.
	 */
	private String utf8EncodedString(String str) {
		try {
			return new String(str.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
		} catch (Exception e) {
			handleException("Error while encoding string to UTF-8", e, "");
			return null;
		}
	}

}
