package br.unb.scrap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.unb.scrap.components.PageScrapper;
import br.unb.scrap.model.Post;
import br.unb.scrap.repository.PostRepository;

/**
 * Classe main, inicializa a aplicacação com spring boot
 *
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private PostRepository repo;

	@Autowired
	private PageScrapper scrapper;

	public static void main(String[] args) {
		System.err.println(">>>>>> ");
		SpringApplication.run(Application.class, args);
		System.err.println("====== ");
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("..................... ");

		fillDataBase();

		System.err.println("++++++++++++++++++++++");
	}

	public void fillDataBase() {
		List<Post> posts = scrapper.execute();
		for (Post post : posts) {
			repo.save(post);
		}

	}

}
