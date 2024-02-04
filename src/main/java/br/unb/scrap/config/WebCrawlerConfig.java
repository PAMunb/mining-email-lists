package br.unb.scrap.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.unb.scrap.logging.FileLogger;

/**
 * Classe de configuração Spring para a aplicação WebCrawler.
 */
@Configuration
public class WebCrawlerConfig {

    /**
     * Método para criar e configurar um bean FileLogger.
     * 
     * @return um bean FileLogger configurado
     */
    @Bean
    FileLogger fileLogger() {
        try {
            return new FileLogger("scrap_log_FileLog.txt");
        } catch (IOException e) {
            System.err.println("Erro ao criar o FileLogger: " + e.getMessage());
            return null;
        }
    }
}
