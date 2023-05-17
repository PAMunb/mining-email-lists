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
//@EntityScan
//@EnableJpaRepositories
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
//		PageScrapper.execute();
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("..................... ");

//		teste01();
		popularBanco();

		System.err.println("++++++++++++++++++++++");
	}

	
	
	public void popularBanco() {
		List<Post> posts = scrapper.execute();
//		System.err.println("Posts: " + posts.size());
//		posts.forEach(System.err::println);
		
		for (Post post : posts) {
			repo.save(post);
		}
		
//		posts = repo.findAll();
//		System.err.println("Posts: " + posts.size());
//		posts.forEach(System.out::println);
	}
	
	private void teste01() {
		Post post = new Post();
		post.setName("Post 1");
		post.setDate("20-08-2022");
		post.setTitle("rejuvenescimento de software");
		post.setOriginal("mensagem original");

		List<Post> posts = repo.findAll();
		System.err.println("Posts: " + posts.size());
		posts.forEach(System.out::println);

		repo.save(post);
		repo.flush();

		posts = repo.findAll();
		System.err.println("Posts: " + posts.size());
		posts.forEach(System.out::println);

	}

}
