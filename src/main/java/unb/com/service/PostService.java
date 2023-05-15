package unb.com.service;

import java.util.Collection;

import unb.com.entities.Post;

/**
 * Classe de servico do @Post
 *
 */
public interface PostService {
	public abstract void createPost(Post Post);

	public abstract void updatePost(String id, Post Post);

	public abstract void deletePost(String id);

	public abstract Collection<Post> getPosts();

}
