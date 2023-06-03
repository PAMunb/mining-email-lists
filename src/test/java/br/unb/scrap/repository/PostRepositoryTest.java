//package br.unb.scrap.repository;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import br.unb.scrap.model.Post;
//
//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
//public class PostRepositoryTest {
//
//	@Mock
//	private PostRepository postRepositoryMock;
//
//	@BeforeEach
//	void init() {
//		MockitoAnnotations.openMocks(this);
//	}
//
//	@Test
//	public void testFindAllPosts() {
//		Post post1 = new Post(1L, "John Doe", "2023-05-19", "Title 1", "Body 1", true, true);
//		Post post2 = new Post(2L, "Jane Smith", "2023-05-20", "Title 2", "Body 2", true);
//		List<Post> posts = new ArrayList<>();
//		posts.add(post1);
//		posts.add(post2);
//
//		when(postRepositoryMock.findAll()).thenReturn(posts);
//
//		List<Post> result = postRepositoryMock.findAll();
//
//		assertEquals(2, result.size());
//		assertEquals(post1, result.get(0));
//		assertEquals(post2, result.get(1));
//	}
//
//	@Test
//	public void testFindPostById() {
//		Post post = new Post(1L, "John Doe", "2023-05-19", "Title 1", "Body 1", true);
//		when(postRepositoryMock.findById(1L)).thenReturn(Optional.of(post));
//
//		Optional<Post> result = postRepositoryMock.findById(1L);
//		assertTrue(result.isPresent());
//		assertEquals(post, result.get());
//	}
//
//}
