package br.unb.scrap;

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

/**
 * Classe main, inicializa a aplicacação com spring boot
 *
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private PostRepository repo;

	@Autowired
	private PageScraper scraper;

	/**
	 * Esta classe é o ponto de entrada para a execução da aplicação
	 * 
	 * @param args O parâmentro args
	 */
	public static void main(String[] args) {
		System.err.println(">>>>>> ");
		SpringApplication.run(Application.class, args);
		System.err.println("====== ");
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("..................... ");

		fillDataBase();

		long postCount = repo.count();
		logger.info("Quantidade de Posts: " + " >>>>>>>>>>>>>>> " + postCount);

		System.err.println("++++++++++++++++++++++");
	}

	/**
	 * Método que serve para popular o banco de dados. Salva os posts recuperados
	 * pelo método chamado execute presente na classe @PageScrapper
	 */
	public void fillDataBase() {
		try {
			List<Post> posts = scraper.execute();
			for (Post post : posts) {
				repo.save(post);
				// repo.findAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
