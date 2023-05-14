package unb.com.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import unb.com.scrapymailists.PageScrapper;

/**
 * Classe main, inicializa a aplicacação com spring boot
 *
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(Application.class, args);

		PageScrapper.execute();
	}

}
