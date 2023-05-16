package unb.com.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import unb.com.components.PageScrapper;

/**
 * Classe main, inicializa a aplicacação com spring boot
 *
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

//		PageScrapper.execute();
	}

}


