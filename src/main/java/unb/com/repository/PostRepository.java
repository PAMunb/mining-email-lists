package unb.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unb.com.model.Post;

/**
 * Classe de repositório do @Post
 *
 */
@Repository
public interface PostRepository extends JpaRepository<Post, String> {

//	Post findByName(String name);
//
//	List<Post> findAll();
//
//	Optional<Post> findById(String id);
//
//	List<Post> findByTitle(String title);

}
