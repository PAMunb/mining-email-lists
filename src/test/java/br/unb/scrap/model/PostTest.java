package br.unb.scrap.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		post.setName(name);
		Assertions.assertEquals(name, post.getName(), "The name was not set correctly.");
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
		post.setDate(date);
		Assertions.assertEquals(date, post.getDate(), "The date was not defined correctly.");
	}

	@Test
	public void testSetAndGetTitle() {
		String title = "Software Rejuvenation";
		post.setTitle(title);
		Assertions.assertEquals(title, post.getTitle(), "The title was not set correctly.");
	}

	@Test
	public void testSetAndGetOriginal() {
		boolean original = false;
		post.setOriginal(original);
		Assertions.assertEquals(original, post.isOriginal(), "Message type not set correctly.");
	}

	@Test
	public void testToString() {
		Long id = 1L;
		String name = "Louis Tatta";
		String date = "04-05-1998";
		String title = "Software Rejuvenation";
		String body = "it is a phenomenon that leads to burnout...software system failure";
		boolean original = false;

		post.setId(id);
		post.setName(name);
		post.setDate(date);
		post.setTitle(title);
		post.setBody(body);
		post.setOriginal(original);

		String expectedString = "Post(id=1, name=Louis Tatta, date=04-05-1998, title=Software Rejuvenation, body=it is a phenomenon that leads to burnout...software system failure, original=false)";
		Assertions.assertEquals(expectedString, post.toString(), "String representation is not correct.");
	}
}