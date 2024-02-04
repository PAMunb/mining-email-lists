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
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import br.unb.scrap.annotations.NotReady;
import br.unb.scrap.domain.Post;
import br.unb.scrap.enums.PostTypeEnum;
import br.unb.scrap.logging.FileLogger;
import br.unb.scrap.scraping.PageScraper;

@Component
//@Primary
@NotReady(reason = "Still under development")
public class ScrapJavaMail implements PageScraper {

	private static final Logger logger = LogManager.getLogger(ScrapJavaMail.class);
	private static final String DATE_TAG_SELECTOR = "tbody > tr > td:nth-child(3)";
	private static final String THREAD_TAG_SELECTOR = "tbody > tr > td:nth-child(4)";
	private static final String DATE_URL_SUFFIX = "date.php";
	private final FileLogger fileLogger;

	public ScrapJavaMail() {
		fileLogger = new FileLogger();
	}

	@Override
	@NotReady(reason = "Still under development")
	public Post scrap(String url) {
		Post post = new Post();
		try {
			Document doc = Jsoup.connect(url).get();

			retrieveAuthorAndDate(doc, post);
			retrieveTitle(doc, post);
			retrieveBody(doc, post);
			retrievePostType(doc, post);

		} catch (Exception e) {
			logger.error("Error while scrapping", e);
			fileLogger.logException("Error while scrapping", url, e);
		}
		return post;
	}

	@NotReady(reason = "Still under development")
	public List<String> getLinksByDate() throws IOException {
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

		} catch (Exception e) {
			logger.error("Error while getting links by date", e);
			fileLogger.logException("Error while getting links by date", BOOST_ARCHIVES_BASE_URL, e);
		}
		return dateUrls;
	}

	@NotReady(reason = "Still under development")
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

	public Set<String> getLinksMessages() throws IOException {
		Set<String> msgs = new HashSet<>();
		List<String> urls = getLinksByDate();
		for (String url : urls) {
			try {
				Connection connection = Jsoup.connect(url);
				Document doc = connection.get();
				for (Element li : extractLiTags(doc)) {
					String link = li.select("a").attr("href");
					msgs.add(url.replace(DATE_URL_SUFFIX, link));
				}
			} catch (Exception e) {
				logger.error("Error while getting links messages for URL: " + url, e);
				fileLogger.logException("Error while getting links messages for URL:", url, e);
			}
		}
		return msgs;
	}

	public List<Post> execute(String baseUrl) throws IOException {
		List<Post> posts = new LinkedList<>();
		Set<String> urls;
		try {
			urls = getLinksMessages();
		} catch (IOException e) {
			logger.error("Error while executing", e);
			return posts;
		}
		for (String url : urls) {
			try {
				Post post = scrap(url);
				posts.add(post);
			} catch (Exception e) {
				logger.error("Error while executing", e);
			}
		}
		logger.info(posts);
		return posts;
	}

	@NotReady(reason = "Still under development")
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

			logger.info("Author: " + name);
			logger.info("Date: " + date);

			post.setAuthorName(utf8EncodedString(name));
			post.setPublicationDate(date);
		} catch (Exception e) {
			logger.error("Error while retrieving author and date", e);
			fileLogger.logException("Error while retrieving author and date", "", e);
		}
	}

	@NotReady(reason = "Still under development")
	public void retrieveTitle(Document doc, Post post) {
		try {
			Elements title = doc.select("title");
			for (Element t : title) {
				String text = t.text();
				text = text.replace("Boost mailing page: ", "");
				logger.info("Title: " + text);
				post.setTitle(utf8EncodedString(text));
			}
		} catch (Exception e) {
			logger.error("Error while retrieving subject", e);
			fileLogger.logException("Error while retrieving subject", "", e);
		}

	}

	@NotReady(reason = "Still under development")
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
			logger.error("Error while retrieving body", e);
			fileLogger.logException("Error while retrieving body", "", e);
		}
	}

	@NotReady(reason = "Still under development")
	protected String getStringBetweenTwoCharacters(String input, String from, String to) {
		try {
			return input.substring(input.indexOf(from) + 1, input.lastIndexOf(to));
		} catch (Exception e) {
			logger.error("Error while getting string between two characters", e);
			fileLogger.logException("Error while getting string between two characters", "", e);
			return null;
		}
	}

	@NotReady(reason = "Still under development")
	protected String utf8EncodedString(String str) {
		try {
			byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
			return new String(bytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			logger.error("Error while encoding string to UTF-8", e);
			fileLogger.logException("Error while encoding string to UTF-8", "", e);
			return null;
		}
	}

	@NotReady(reason = "Still under development")
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
			logger.error("Error while retrieving post type", e);
			fileLogger.logException("Error while retrieving post type", "", e);
			return false;
		}
	}

}
