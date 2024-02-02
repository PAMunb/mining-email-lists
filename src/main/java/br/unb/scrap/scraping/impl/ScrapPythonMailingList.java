package br.unb.scrap.scraping.impl;

import static br.unb.scrap.utils.UrlUtils.PYTHON_LIST_MAILING_LIST_BASE_URL;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import br.unb.scrap.domain.Post;
import br.unb.scrap.enums.PostTypeEnum;
import br.unb.scrap.logging.FileLogger;
import br.unb.scrap.scraping.PageScraper;

@Component
@Primary
public class ScrapPythonMailingList implements PageScraper {

	private static final Logger logger = LogManager.getLogger(ScrapPythonMailingList.class);
	private static final String TR_TAG_SELECTOR = "tbody > tr";
	private static final String DATE_TAG_SELECTOR = "td:nth-child(2)";
	private static final String THREAD_TAG_SELECTOR = "tbody > tr > td:nth-child(1)";
	private static final String DATE_URL_SUFFIX = "date.html";
	private final FileLogger fileLogger;

	public ScrapPythonMailingList() {
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
	 * documento HTML, a partir da PYTHON_LIST_MAILING_LIST_BASE_URL ou da url
	 * passada.
	 * 
	 * @return Retorna a lista urls contendo as URLs das threads extraídas.
	 */
	public List<String> getLinksByDate() throws IOException {
		List<String> dateUrls = new LinkedList<>();
		try {
			Document doc = Jsoup.connect(PYTHON_LIST_MAILING_LIST_BASE_URL).get();
			Elements tables = doc.select("table");
			for (Element table : tables) {
				Elements tr_tags = table.select(TR_TAG_SELECTOR);
				tr_tags.remove(0); // remove cabecalho da tabela
				for (Element tag : tr_tags) {
					Elements dateColumns = tag.select(DATE_TAG_SELECTOR);
					for (Element column : dateColumns) {
						Elements links = column.select("a");
						if (links.size() > 0) {
							for (Element link : links) {
								if (link.text().endsWith("[ Date ]")) {
									String url = PYTHON_LIST_MAILING_LIST_BASE_URL + link.attr("href");
									logger.info(url); // debug
									dateUrls.add(url);
								}
							}
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
	 * HTML, a partir da PYTHON_LIST_MAILING_LIST_BASE_URL ou da url passada.
	 * 
	 * @return Retorna a lista urls contendo as URLs das threads extraídas.
	 */
	public List<String> getLinksByThread() throws IOException {
		List<String> threadUrls = new LinkedList<>();
		try {
			Document doc = Jsoup.connect(PYTHON_LIST_MAILING_LIST_BASE_URL).get();
			Elements tables = doc.select("table");
			for (Element table : tables) {
				Elements threadColumns = table.select(THREAD_TAG_SELECTOR);
				for (Element column : threadColumns) {
					Elements links = column.select("a");
					if (links.size() > 0) {
						Element link = links.first();
						if (link.text().endsWith("Thread")) {
							String url = PYTHON_LIST_MAILING_LIST_BASE_URL + link.attr("href");
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
			Element ulParent = doc.select("ul").get(1);
			Elements lis = ulParent.select("li");
			for (Element li : lis) {
				liTags.add(li);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return liTags;
	}

	/**
	 * Método é responsável por obter os links das mensagens a partir das URLs das
	 * threads organizadas por data.
	 * 
	 * @return Retorna um conjunto msgs contendo os links das mensagens extraídas.
	 */

	public Set<String> getLinksMessages() throws IOException {
		Set<String> msgs = new HashSet<String>();
		List<String> urls = getLinksByDate();
		for (String url : urls) {
			try {
				Connection connection = Jsoup.connect(url);
				Document doc = connection.get();
				for (Element li : extractLiTags(doc)) {
					String link = li.select("a").first().attr("href");
					msgs.add(url.replace(DATE_URL_SUFFIX, link));
				}
				// break; // debug
			} catch (Exception e) {
				logger.error("Error while getting links messages for URL: " + url, e);
				fileLogger.logException("Error while getting links messages for URL:", "url", e);
			}
		}
		logger.info(msgs.size()); // debug
		return msgs;
	}

	/**
	 * Método que percorre uma lista de URLs, faz a raspagem de dados dessas URLs
	 * para criar objetos Post e armazena esses objetos em uma lista.
	 * 
	 * @return Retorna uma lista de objetos do tipo Post
	 */
	/**
	 * Método que percorre uma lista de URLs, faz a raspagem de dados dessas URLs
	 * para criar objetos Post e armazena esses objetos em uma lista.
	 * 
	 * @param baseUrl A URL base para a qual você deseja executar a raspagem.
	 * @return Retorna uma lista de objetos do tipo Post
	 */
	public List<Post> execute(String baseUrl) throws IOException {
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
			Element author = doc.select("b").first();
			String authorName = author.text();

			Element date = doc.select("i").first();
			String daring = date.text().substring(4); // remove dia da semana

			logger.info("Autor: " + authorName); // debug
			logger.info("Data: " + daring); // debug

			post.setAuthorName(utf8EncodedString(authorName));
			post.setPublicationDate(daring);
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
			Elements title = doc.select("h1	");
			for (Element t : title) {
				String text = t.text();
				logger.info("title: " + text); // debug
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
			Elements textsection = doc.select("pre");
			String body = textsection.text();
			post.setBody(utf8EncodedString(body));
			// System.out.println(post.getBody()); // debug
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
		try {
			String searchTerm = "Reply";
			String html = doc.html();

			boolean isOriginal = html.contains(searchTerm);

			if (isOriginal) {
				logger.info("This is the original email >>>>>> true");
				post.setPostType(PostTypeEnum.ORIGINAL);
			} else {
				logger.info("This is the reply email >>>>>> false");
				post.setPostType(PostTypeEnum.REPLY);
			}

			return isOriginal;
		} catch (Exception e) {
			logger.error("Error while retrieving post Type", e);
			fileLogger.logException("Error while retrieving post Type", "url", e);
			return false;
		}
	}

}