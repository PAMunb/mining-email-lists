//package unb.com.repository;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.annotation.DirtiesContext;
//
//import unb.com.model.Post;
//
//@DataJpaTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//public class PostRepositorytTest {
//
//	@Autowired
//	private TestEntityManager entityManager;
//
//	@Autowired
//	private PostRepository PostRepository;
//
//	@Test
//	public void testSavePost() {
//		Post Post = new Post();
//		Post.setName("Post 1");
//		Post.setDate("20-08-2022");
//		Post.setTitle("rejuvenescimento de software");
//		Post.setOriginal("mensagem original");
//
//		Post savedPost = PostRepository.save(Post);
//
//		Assertions.assertNotNull(savedPost.getId());
//	}
//
//	@Test
//	public void testFindPostById() {
//		Post Post = new Post();
//		Post.setName("Post 2");
//		Post.setDate("04-05-1998");
//		Post.setTitle("aaaaaaaaaaa");
//		Post.setOriginal("original");
//
//		entityManager.persist(Post);
//		entityManager.flush();
//		entityManager.clear();
//
//		String PostId = Post.getId();
//		Post foundPost = PostRepository.findById(PostId).orElse(null);
//
//		Assertions.assertNotNull(foundPost);
//		Assertions.assertEquals("Post 2", foundPost.getName());
//		Assertions.assertEquals("aaaaaaaaaaa", foundPost.getTitle());
//		Assertions.assertEquals("original", foundPost.getOriginal());
//	}
//
//	@Test
//	public void testDeletePost() {
//		Post Post = new Post();
//		Post.setName("Post 3");
//		Post.setDate("12-05-2022");
//		Post.setTitle("software");
//		Post.setOriginal("mensagem original");
//
//		entityManager.persist(Post);
//		entityManager.flush();
//		entityManager.clear();
//
//		String PostId = Post.getId();
//		PostRepository.deleteById(PostId);
//
//		Assertions.assertFalse(PostRepository.existsById(PostId));
//	}
//}
