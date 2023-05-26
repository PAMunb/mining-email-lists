package br.unb.scrap.components;

import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import br.unb.scrap.model.Post;

public abstract class PageScrapper extends ConnectionJsoup {

	/**
	 * Método que percorre uma lista de URLs, faz a raspagem de dados dessas URLs
	 * para criar objetos Post e armazena esses objetos em uma lista.
	 * 
	 * @return Retorna uma lista de objetos do tipoo Post
	 */
	public abstract List<Post> execute();

	/**
	 * Método responsável por fazer a extração de informações de uma página web com
	 * base em uma URL fornecida
	 * 
	 * @param url Passando a url como parâmetro
	 * @return Retorna um objeto do tipo Post contendo informações.
	 */
	protected abstract Post scrap(String url);

	/**
	 * Método é responsável por obter os links das threads a partir de um documento
	 * HTML, a partir da BASE_URL ou da url passada.
	 * 
	 * @return Retorna a lista urls contendo as URLs das threads extraídas.
	 */
	protected abstract List<String> getLinksByThread();

	/**
	 * Método é responsável por extrair as tags li de um documento HTML.
	 * 
	 * @param doc passando o document como parâmentro
	 * @return Retorna a coleção liTags contendo todas as tags li extraídas.
	 */
	protected abstract Elements extractLiTags(Document doc);

	/**
	 * Método é responsável por obter os links das mensagens a partir das URLs das
	 * threads
	 * 
	 * @return Retorna um conjunto msgs contendo os links das mensagens extraídas.
	 */

	protected abstract Set<String> getLinksMessages();

	/**
	 * Método que extrai o autor e a data do post a partir do documento HTML
	 * fornecido. Ele seleciona o primeiro elemento de parágrafo dentro do elemento
	 * body p, remove as tags em desse elemento e divide o texto resultante em duas
	 * partes para obter o nome do autor e a data do post
	 * 
	 * @param doc  passando o doc como parâmentro
	 * @param post passando o post como parâmetro
	 */
	protected abstract void retrieveAuthorAndDate(Document doc, Post post);

	/**
	 * Método que extrai o conteúdo do título do post a partir do documento HTML
	 * fornecido. Ele seleciona o elemento de título no documento, obtém o texto
	 * desse elemento e define esse texto como o título do post no objeto Post
	 * 
	 * @param doc  passando o doc como parâmentro
	 * @param post passando o post como parâmetro
	 */
	protected abstract void retrieveTitle(Document doc, Post post);

	/**
	 * Método que extrai o conteúdo do corpo do post a partir do documento HTML
	 * fornecido. Ele obtém o texto entre as tags start e end do body. no documento,
	 * obtém o texto de cada parágrafo e define esse texto como o corpo do post no
	 * objeto Post
	 * 
	 * @param doc  passando o doc como parâmentro
	 * @param post passando o post como parâmetro
	 */
	protected abstract void retrieveBody(Document doc, Post post);

	/**
	 * 
	 * Método booleano que verifica se a palavra-chave ("Reply") está presente no
	 * conteúdo HTML de um documento. Se for encontrada, define se o post é um email
	 * original (true) e se não for encontrada, define que é um email de resposta
	 * (false).
	 * 
	 * @param doc  passando o doc como parâmentro
	 * @param post passando o post como parâmetro
	 */
	protected abstract boolean retrieveIsOriginal(Document doc, Post post);

}
