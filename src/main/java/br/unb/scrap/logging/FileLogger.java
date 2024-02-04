package br.unb.scrap.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Classe para registrar exceções em um arquivo de log.
 */
@Component
public class FileLogger {

	private static final Logger logger = LogManager.getLogger(FileLogger.class);
	private PrintWriter writer;

	/**
	 * Construtor que cria um novo arquivo de log com o nome especificado.
	 *
	 * @param fileName o nome do arquivo de log
	 * @throws IOException se ocorrer um erro de E/S ao criar o arquivo
	 */
	public FileLogger(String fileName) throws IOException {
		writer = new PrintWriter(new FileWriter(fileName));
		logger.info("Arquivo de log criado: " + fileName);
	}

	/**
	 * Registra uma exceção no arquivo de log, juntamente com informações sobre o
	 * método e a URL associados.
	 *
	 * @param method    o método associado à exceção
	 * @param url       a URL associada à exceção
	 * @param exception a exceção a ser registrada
	 */
	public void logException(String method, String url, Exception exception) {
		writer.println("Date and Time: " + LocalDateTime.now());
		writer.println("Method: " + method);
		writer.println("URL: " + url);
		writer.println("Exception: " + exception.getClass().getSimpleName() + ": " + exception.getMessage());
		exception.printStackTrace(writer);
		writer.println();
		writer.flush();

		// Registro da exceção no logger
		logger.error("Exceção no método " + method + ", URL: " + url, exception);
	}

	/**
	 * Fecha o arquivo de log.
	 */
	public void close() {
		writer.close();
	}
}
