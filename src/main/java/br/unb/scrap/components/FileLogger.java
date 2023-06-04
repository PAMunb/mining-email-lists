package br.unb.scrap.components;

import java.io.FileWriter;
import java.io.IOException;

/**
 * A classe para gravar exceções em um arquivo. Ela cria um arquivo de log
 * chamado "logs.txt" e permite registrar exceções juntamente com a URL
 * associada. O arquivo de log é fechado quando o método close() é chamado.
 */
public class FileLogger {

	private FileWriter fileWriter;

	/**
	 * Constrói um objeto FileLogger e cria um arquivo de log chamado "logs.txt". Se
	 * ocorrer uma IOException durante a criação do arquivo, ela é impressa na saída
	 * de erro padrão.
	 */
	public FileLogger() {
		try {
			fileWriter = new FileWriter("logs.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Registra a exceção especificada e sua URL associada no arquivo de log. A
	 * mensagem da exceção e a URL são gravadas em linhas separadas, seguidas por
	 * uma linha em branco. Se ocorrer uma IOException durante o processo de
	 * escrita, ela é impressa na saída de erro padrão.
	 * 
	 *@param metodo       O método associado à exceção
	 * @param url       a URL associada à exceção
	 * @param exception a exceção a ser registrada
	 */
	public void logException(String metodo, String url, Exception exception) {
		try {
			fileWriter.write("Método: " + metodo + "\n");
			fileWriter.write("URL: " + url + "\n");
			fileWriter.write("Exceção: " + exception.getMessage() + "\n\n");
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fecha o arquivo de log. Se ocorrer uma IOException durante o processo de
	 * fechamento, ela é impressa na saída de erro padrão.
	 */
	public void close() {
		try {
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
