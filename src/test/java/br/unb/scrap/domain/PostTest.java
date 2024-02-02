package br.unb.scrap.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import br.unb.scrap.enums.PostTypeEnum;

class PostTest {

	@Test
	public void testEqualsAndHashCode() {
		Post post1 = new Post(1L, "Pedro", "2022-02-01", "Title 1","O sol brilha intensamente no céu azul.", true,
				PostTypeEnum.ORIGINAL);
		Post post2 = new Post(1L, "Rafaela", "2022-02-01", "Title 1", "Um gato preto atravessa a rua silenciosamente",
				true, PostTypeEnum.ORIGINAL);
		Post post3 = new Post(2L, "Lucas", "2022-02-02", "Title 2", "O aroma do café recém-preparado preenche a sala.",
				true, PostTypeEnum.REPLY);

		assertEquals(post1, post2);
		assertEquals(post1.hashCode(), post2.hashCode());

		assertNotEquals(post1, post3);
		assertNotEquals(post1.hashCode(), post3.hashCode());
	}

//	@Test
//	public void testToString() {
//		Post post = new Post(1L, "Pedro", "2022-02-01", "Title 1", "O sol brilha intensamente no céu azul.", true, PostTypeEnum.ORIGINAL);
//
//		assertEquals(
//				"Post(id=1, authorName=Rafaela, publicationDate=2022-02-01, title=Title 1, body=Um gato preto atravessa a rua silenciosamente, original=true, postTypeEnum=ORIGINAL)",
//				post.toString());
//	}
}
