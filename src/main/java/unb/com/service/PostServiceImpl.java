package unb.com.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import unb.com.entities.Post;

/**
 * Classe de implementação servico do @Post
 *
 */
@Service
public class PostServiceImpl implements PostService {

	private static Map<String, Post> PostRepo = new HashMap<>();
	static {
		Post p = new Post();
		p.setId("1");
		p.setName("teste1");

		Post t = new Post();
		t.setId("2");
		t.setName("teste2");
		PostRepo.put(t.getId(), t);
	}

	@Override
	public void createPost(Post Post) {
		PostRepo.put(Post.getId(), Post);
	}

	@Override
	public void updatePost(String id, Post Post) {
		PostRepo.remove(id);
		Post.setId(id);
		PostRepo.put(id, Post);
	}

	@Override
	public void deletePost(String id) {
		PostRepo.remove(id);

	}

	@Override
	public Collection<Post> getPosts() {
		return PostRepo.values();
	}
}
