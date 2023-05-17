package br.unb.scrap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unb.scrap.model.Post;

/**
 * Classe de repositório do @Post
 *
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


}
