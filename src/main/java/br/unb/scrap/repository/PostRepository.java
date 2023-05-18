package br.unb.scrap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unb.scrap.model.Post;

/**
 * Classe de repositório do @Post estendendo a JPARepository que por sua vez,
 * estende outros repositórios e divermos elementos de CRUD, Ex: FindAll,
 * FindByID;
 *
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
