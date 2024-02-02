package br.unb.scrap.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.unb.scrap.domain.Post;
import br.unb.scrap.repository.PostRepository;

@Service
public class PostService {

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public List<Post> findPostsByAuthorAndBody(String authorName, String body) {
		return postRepository.findByAuthorNameAndBody(authorName, body);
	}
}