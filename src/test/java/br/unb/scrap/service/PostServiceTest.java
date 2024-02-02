package br.unb.scrap.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unb.scrap.domain.Post;
import br.unb.scrap.repository.PostRepository;

class PostServiceTest {

	private PostService postService;
	private PostRepository postRepository;

	@BeforeEach
	public void setup() {
		postRepository = mock(PostRepository.class);
		postService = new PostService(postRepository);
	}

	@Test
	public void testFindPostsByAuthorAndBody() {
		String authorName = "Alana Paula";
		String body = "Teste Aleatório";

		Post post1 = new Post();
		post1.setAuthorName(authorName);
		post1.setBody(body);

		Post post2 = new Post();
		post2.setAuthorName(authorName);
		post2.setBody(body);

		List<Post> expectedPosts = Arrays.asList(post1, post2);

		when(postRepository.findByAuthorNameAndBody(authorName, body)).thenReturn(expectedPosts);

		List<Post> actualPosts = postService.findPostsByAuthorAndBody(authorName, body);

		assertEquals(expectedPosts, actualPosts);
	}
}
