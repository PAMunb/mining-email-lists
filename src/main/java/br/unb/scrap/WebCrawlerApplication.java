package br.unb.scrap;

import static br.unb.scrap.utils.UrlUtils.BOOST_ARCHIVES_BASE_URL;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.unb.scrap.dataexport.DataExporter;
import br.unb.scrap.domain.Post;
import br.unb.scrap.repository.PostRepository;
import br.unb.scrap.scraping.PageScraper;

/**
 * Classe principal que representa a aplicação de web crawler.
 */
@SpringBootApplication
public class WebCrawlerApplication implements CommandLineRunner {

	private static final Logger logger = LogManager.getLogger(WebCrawlerApplication.class);

	@Autowired
	private PostRepository repo;

	@Autowired
	private PageScraper scraper;

	@Autowired
	private DataExporter dataExporter;

	/**
	 * Método principal que inicia a aplicação web crawler.
	 *
	 * @param args os argumentos fornecidos ao iniciar a aplicação.
	 */
	public static void main(String[] args) {
		logger.info(">>>>>> Starting Application");
		SpringApplication.run(WebCrawlerApplication.class, args);
		logger.info("======================= fim :) ==================");
	}

	/**
	 * Método responsável por iniciar a aplicação, preencher o banco de dados com
	 * dados obtidos a partir de uma URL específica e exportar os dados para CSV e
	 * JSON.
	 * 
	 * @param args os argumentos passados para a aplicação
	 * @throws Exception se ocorrer um erro durante a execução da aplicação
	 */
	@Override
	public void run(String... args) throws Exception {
		logger.info("..................... Running Application");

		fillDataBase(BOOST_ARCHIVES_BASE_URL);
		// fillDataBase(OPENJDK_MAILING_LIST_BASE_URL);
		// fillDataBase(PYTHON_LIST_MAILING_LIST_BASE_URL);
		// fillDataBase(JAVA_MAIL_ARCHIVE_BASE_URL);

		logger.info("*************************************************************");
		logger.info(">>>>>>>>>>>>>>>>>>Scraping finalizado<<<<<<<<<<<<<<<<<<<<<");
		long postCount = repo.count();
		logger.info("Quantidade de Posts: " + " >>>>>>>>>>>>>>> " + postCount);

		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Exportando Scraping para arquivo .CSV<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		if (postCount > 0) {
			logger.info("Iniciando exportação de dados...");
			dataExporter.exportData();
			logger.info("Exportação de dados concluída com sucesso.");
		} else {
			logger.warn("Nenhum post foi encontrado. Exportação de dados não realizada.");
		}

	}

	/**
	 * Preenche o banco de dados com os dados obtidos a partir da URL especificada.
	 *
	 * @param url a URL da qual os dados serão obtidos para preencher o banco de
	 *            dados.
	 * @throws IOException se ocorrer um erro de entrada/saída durante a execução da
	 *                     operação.
	 */
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
