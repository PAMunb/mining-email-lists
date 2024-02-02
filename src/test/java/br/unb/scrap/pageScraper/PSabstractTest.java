package br.unb.scrap.pageScraper;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import br.unb.scrap.config.ConnectionJsoup;
import br.unb.scrap.domain.Post;

/**
 * Classe abstrata que define um esqueleto para realizar a raspagem de dados de
 * uma página web
 * Não utilizada porque optamos por utiizar uma Interface
 */

@Deprecated
public abstract class PSabstractTest extends ConnectionJsoup {

	/**
	 * Método abstrato que percorre uma lista de URLs, faz a raspagem de dados
	 * dessas URLs para criar objetos Post e armazena esses objetos em uma lista.
	 *
	 * @return Retorna uma lista de objetos do tipo Post
	 * @throws IOException
	 */
	public abstract List<Post> execute() throws IOException;

	/**
	 * Método responsável por fazer a extração de informações de uma página web com
	 * base em uma URL fornecida.
	 *
	 * @param url A URL da página a ser raspada
	 * @return Retorna um objeto do tipo Post contendo informações.
	 */
	protected abstract Post scrap(String url);

	/**
	 * Método responsável por obter os links das threads a partir de um documento
	 * HTML, a partir da BASE_URL ou da URL passada.
	 *
	 * @return Retorna a lista de URLs das threads extraídas.
	 */
	protected abstract List<String> getLinksByThread() throws IOException;

	/**
	 * Método responsável por obter os links dos posts por data a partir de um
	 * documento HTML, a partir da BASE_URL ou da URL passada.
	 *
	 * @return Retorna a lista de URLs dos posts por data extraídos.
	 * @throws IOException
	 */
	protected abstract List<String> getLinksByDate() throws IOException;

	/**
	 * Método responsável por extrair as tags li de um documento HTML.
	 *
	 * @param doc O documento HTML
	 * @return Retorna a coleção de tags li extraídas.
	 */
	protected abstract Elements extractLiTags(Document doc);

	/**
	 * Método responsável por obter os links das mensagens a partir das URLs das
	 * threads.
	 *
	 * @return Retorna um conjunto de URLs das mensagens extraídas.
	 */
	protected abstract Set<String> getLinksMessages() throws IOException;

	/**
	 * Método que extrai o autor e a data do post a partir do documento HTML
	 * fornecido.
	 *
	 * @param doc  O documento HTML
	 * @param post O objeto Post a ser preenchido com as informações de autor e
	 *             data.
	 */
	protected abstract void retrieveAuthorAndDate(Document doc, Post post);

	/**
	 * Método que extrai o assunto do post a partir do documento HTML fornecido.
	 *
	 * @param doc  O documento HTML
	 * @param post O objeto Post a ser preenchido com o assunto.
	 */
	protected abstract void retrieveTitle(Document doc, Post post);

	/**
	 * Método que extrai o conteúdo do corpo do post a partir do documento HTML
	 * fornecido.
	 *
	 * @param doc  O documento HTML
	 * @param post O objeto Post a ser preenchido com o conteúdo do corpo.
	 */
	protected abstract void retrieveBody(Document doc, Post post);

	/**
	 * Método que verifica se a palavra-chave ("Reply") está presente no conteúdo
	 * HTML de um documento.
	 *
	 * @param doc  O documento HTML
	 * @param post O objeto Post a ser preenchido com o tipo de post.
	 * @return Retorna true se a palavra-chave for encontrada, false caso contrário.
	 */
	protected abstract boolean retrievePostType(Document doc, Post post);

}