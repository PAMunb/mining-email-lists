package br.unb.scrap.service;

import java.util.Collection;

import br.unb.scrap.model.Post;

/**
 * Classe de servico do @Post
 *
 */
public interface PostService<I> {
	/**
	 * @param Post
	 */
	public abstract void createPost(Post Post);

	/**
	 * @param id
	 * @param Post
	 */
	public abstract void updatePost(Post Post);

	/**
	 * @param id
	 */
	public abstract void deletePost(I id);

	/**
	 * @return
	 */
	public abstract Collection<Post> getPosts();

	/**
	 * @param post
	 */
	public abstract void savePost(Post post);

}
