package unb.com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import unb.com.entities.Post;

/**
 * Classe de repositório do @Post
 *
 */
public interface PostRepository extends JpaRepository<Post, String> {

	Post findByName(String name);

	List<Post> findAll();

	Optional<Post> findById(Long id);

	Post findByNameAndDate(String name, String date);

}
//
//	@PersistenceContext
//	private EntityManager entityManager;
