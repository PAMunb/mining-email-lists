package br.unb.scrap.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unb.scrap.enums.PostType;

public class PostTest {

	private Post post;

	@BeforeEach
	public void setup() {
		post = new Post();
	}

	@Test
	public void testSetAndGetId() {
		Long id = 1L;
		post.setId(id);
		Assertions.assertEquals(id, post.getId(), "The ID was not set correctly.");
	}

	@Test
	public void testSetAndGetName() {
		String name = "Louis Tatta";
		post.setAuthorName(name);
		Assertions.assertEquals(name, post.getAuthorName(), "The name was not set correctly.");
	}

	@Test
	public void testSetAndGetbody() {
		String body = "it is a phenomenon that leads to burnout...software system failure";
		post.setBody(body);
		Assertions.assertEquals(body, post.getBody(), "The body was not defined correctly.");
	}

	@Test
	public void testSetAndGetDate() {
		String date = "04-05-2005";
		post.setPublicationDate(date);
		Assertions.assertEquals(date, post.getPublicationDate(), "The date was not defined correctly.");
	}

	@Test
	public void testSetAndGetTitle() {
		String title = "Software Rejuvenation";
		post.setTitle(title);
		Assertions.assertEquals(title, post.getTitle(), "The title was not set correctly.");
	}

//	@Test
//	public void testSetAndGetOriginal() {
//		PostType postType = PostType.ORIGINAL;
//		Assertions.assertEquals(postType, post.getPostType(), "Message type not set correctly.");
//	}

	@Test
	public void testToString() {
		Long id = 1L;
		String name = "Louis Tatta";
		String date = "04-05-1998";
		String subject = "Software Rejuvenation";
		String body = "it is a phenomenon that leads to burnout...software system failure";
		PostType postType = PostType.ORIGINAL;

		post.setId(id);
		post.setAuthorName(name);
		post.setPublicationDate(date);
		post.setTitle(subject);
		post.setBody(body);
		post.setPostType(postType);

		String expectedString = "Post(id=1, authorName=Louis Tatta, publicationDate=04-05-1998, subject=Software Rejuvenation, body=it is a phenomenon that leads to burnout...software system failure, postType=ORIGINAL)";
		Assertions.assertEquals(expectedString, post.toString(), "String representation is not correct.");
	}
}