package unb.com.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unb.com.model.Post;
import unb.com.repository.PostRepository;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostRepository repository;

	@GetMapping("/posts")
	public ResponseEntity<List<Post>> listPosts() throws IOException {
		List<Post> posts = repository.findAll();
		return ResponseEntity.ok(posts);
	}

//	@GetMapping("/allposts")
//	public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = false) String title) {
//		try {
//			List<Post> Posts = new ArrayList<Post>();
//
//			if (title == null)
//				repository.findAll().forEach(Posts::add);
//			else
//				repository.findByTitle(title).forEach(Posts::add);
//
//			if (Posts.isEmpty()) {
//				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//			}
//
//			return new ResponseEntity<>(Posts, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
}
