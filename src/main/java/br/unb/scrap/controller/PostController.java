//package br.unb.scrap.controller;
//
//import java.util.Collections;
//import java.util.List;
//
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import br.unb.scrap.domain.Post;
//import br.unb.scrap.service.PostService;
//
//@Deprecated
//@RestController
//@RequestMapping("/api/posts")
//public class PostController {
//
//	private final PostService postService;
//
//	public PostController(PostService postService) {
//		this.postService = postService;
//	}
//
//	@SuppressWarnings("deprecation")
//	@GetMapping("/search")
//	public List<Post> searchPosts(@RequestParam(value = "author", required = false) String author,
//			@RequestParam(value = "body", required = false) String body) {
//		if (StringUtils.isEmpty(author) && StringUtils.isEmpty(body)) {
//			return Collections.emptyList();
//		}
//
//		return postService.findPostsByAuthorAndBody(author, body);
//	}
//}
