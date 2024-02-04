package br.unb.scrap;

//import static br.unb.scrap.utils.UrlUtils.BOOST_ARCHIVES_BASE_URL;
import static br.unb.scrap.utils.UrlUtils.OPENJDK_MAILING_LIST_BASE_URL;
//import static br.unb.scrap.utils.UrlUtils.PYTHON_LIST_MAILING_LIST_BASE_URL;
//import static br.unb.scrap.utils.UrlUtils.JAVA_MAIL_ARCHIVE_BASE_URL;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.unb.scrap.domain.Post;
import br.unb.scrap.repository.PostRepository;
import br.unb.scrap.scraping.PageScraper;

@SpringBootApplication
public class WebCrawlerApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(WebCrawlerApplication.class);

	@Autowired
	private PostRepository repo;

	@Autowired
	private PageScraper scraper;

	public static void main(String[] args) {
		logger.info(">>>>>> Starting Application");
		SpringApplication.run(WebCrawlerApplication.class, args);
		logger.info("====== Application Started");
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("..................... Running Application");

//		fillDataBase(BOOST_ARCHIVES_BASE_URL);
		fillDataBase(OPENJDK_MAILING_LIST_BASE_URL);
//		fillDataBase(PYTHON_LIST_MAILING_LIST_BASE_URL);
//		fillDataBase(JAVA_MAIL_ARCHIVE_BASE_URL);

		long postCount = repo.count();
		logger.info("Quantidade de Posts: " + " >>>>>>>>>>>>>>> " + postCount);

		logger.info("++++++++++++++++++++++");
	}

	public void fillDataBase(String url) throws IOException {
		try {
			List<Post> posts = scraper.execute(url);
			for (Post post : posts) {
				repo.save(post);
			}
			logger.info("Data filled successfully from " + url);
		} catch (Exception e) {
			logger.error("Unexpected error while filling the database with data from " + url, e);
		}
	}
}