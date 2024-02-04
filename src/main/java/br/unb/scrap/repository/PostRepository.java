package br.unb.scrap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unb.scrap.domain.Post;

/**
 * Interface de repositório para a entidade Post.
 * 
 * Esta interface estende JpaRepository, fornecendo métodos para realizar
 * operações CRUD (Create, Read, Update, Delete) em objetos Post no banco de
 * dados.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

	/**
	 * Busca posts por nome do autor.
	 * 
	 * @param authorName o nome do autor a ser pesquisado
	 * @return uma lista de posts
	 */
	List<Post> findByAuthorName(String authorName);

}
