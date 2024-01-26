package br.unb.scrap;

import static br.unb.scrap.utils.UrlUtils.BASE_URL_BOOST;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.unb.scrap.components.PageScraper;
import br.unb.scrap.model.Post;
import br.unb.scrap.repository.PostRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private PostRepository repo;

	@Autowired
	private PageScraper scraper;

	public static void main(String[] args) {
		logger.info(">>>>>> Starting Application");
		SpringApplication.run(Application.class, args);
		logger.info("====== Application Started");
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("..................... Running Application");

		fillDataBase(BASE_URL_BOOST);
		// fillDataBase("url_page2");

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