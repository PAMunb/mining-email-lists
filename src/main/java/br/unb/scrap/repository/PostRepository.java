package br.unb.scrap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unb.scrap.model.Post;

/**
 * Classe de repositório do @Post estendendo a JPARepository que por sua vez,
 * estende outros repositórios e divermos elementos de CRUD, Ex: FindAll,
 * FindByID;
 *
 */
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByAuthorNameAndBody(String authorName, String body);

}