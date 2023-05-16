package unb.com.service;

import java.util.Collection;

import unb.com.model.Post;

/**
 * Classe de servico do @Post
 *
 */
public interface PostService {
	/**
	 * @param Post
	 */
	public abstract void createPost(Post Post);

	/**
	 * @param id
	 * @param Post
	 */
	public abstract void updatePost(String id, Post Post);

	/**
	 * @param id
	 */
	public abstract void deletePost(String id);

	/**
	 * @return
	 */
	public abstract Collection<Post> getPosts();

	/**
	 * @param post
	 */
	public abstract void savePost(Post post);

}
