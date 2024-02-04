package br.unb.scrap.scraping;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import br.unb.scrap.domain.Post;

/**
 * Interface que define os métodos necessários para realizar o scraping de uma
 * página.
 */
public interface PageScraper {

	/**
	 * Executa o scraping de dados de uma página.
	 *
	 * @param url a URL da página a ser scraped.
	 * @return uma lista de objetos Post obtidos da página.
	 * @throws IOException se ocorrer um erro de leitura ou conexão.
	 */
	List<Post> execute(String url) throws IOException;

	/**
	 * Extrai os dados de um post a partir de uma URL específica.
	 *
	 * @param url a URL do post a ser scraped.
	 * @return o objeto Post correspondente ao post scraped.
	 */
	Post scrap(String url);

	/**
	 * Obtém os links presentes em uma página, organizados por threads.
	 *
	 * @return uma lista de URLs dos links encontrados.
	 * @throws IOException se ocorrer um erro de leitura ou conexão.
	 */
	List<String> getLinksByThread() throws IOException;

	/**
	 * Obtém os links presentes em uma página, organizados por data.
	 *
	 * @return uma lista de URLs dos links encontrados.
	 * @throws IOException se ocorrer um erro de leitura ou conexão.
	 */
	List<String> getLinksByDate() throws IOException;

	/**
	 * Extrai os elementos `li` de um documento HTML.
	 *
	 * @param doc o documento HTML a ser analisado.
	 * @return os elementos `li` encontrados no documento.
	 */
	Elements extractLiTags(Document doc);

	/**
	 * Obtém os links presentes nas mensagens de um post.
	 *
	 * @return um conjunto de URLs dos links encontrados.
	 * @throws IOException se ocorrer um erro de leitura ou conexão.
	 */
	Set<String> getLinksMessages() throws IOException;

	/**
	 * Recupera o autor e a data de um post a partir do documento HTML.
	 *
	 * @param doc  o documento HTML que contém as informações do post.
	 * @param post o post ao qual as informações devem ser atribuídas.
	 */
	void retrieveAuthorAndDate(Document doc, Post post);

	/**
	 * Recupera o título de um post a partir do documento HTML.
	 *
	 * @param doc  o documento HTML que contém o título do post.
	 * @param post o post ao qual o título deve ser atribuído.
	 */
	void retrieveTitle(Document doc, Post post);

	/**
	 * Recupera o conteúdo do corpo de um post a partir do documento HTML.
	 *
	 * @param doc  o documento HTML que contém o corpo do post.
	 * @param post o post ao qual o conteúdo do corpo deve ser atribuído.
	 */
	void retrieveBody(Document doc, Post post);

	/**
	 * Verifica o tipo de post a partir do documento HTML e o atribui ao post.
	 *
	 * @param doc  o documento HTML que contém as informações do post.
	 * @param post o post ao qual o tipo deve ser atribuído.
	 * @return true se o tipo de post for recuperado com sucesso, false caso
	 *         contrário.
	 */
	boolean retrievePostType(Document doc, Post post);

	/**
	 * Método que percorre uma lista de URLs, realiza o scraping de dados dessas
	 * URLs para criar objetos Post e armazena esses objetos em uma lista.
	 *
	 * @return uma lista de objetos Post.
	 */
}
