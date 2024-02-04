package br.unb.scrap.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.unb.scrap.domain.Post;

class PostRepositoryTest {

	@Test
	public void testFindByAuthorNameAndBody() {
		PostRepository postRepository = mock(PostRepository.class);

		String authorName = "Alana Paula";

		Post post1 = new Post();
		post1.setAuthorName(authorName);

		Post post2 = new Post();
		post2.setAuthorName(authorName);

		List<Post> expectedPosts = Arrays.asList(post1, post2);

		when(postRepository.findByAuthorName(authorName)).thenReturn(expectedPosts);

		List<Post> actualPosts = postRepository.findByAuthorName(authorName);

		assertEquals(expectedPosts, actualPosts);
	}
}
