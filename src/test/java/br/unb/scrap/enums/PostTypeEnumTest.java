package br.unb.scrap.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PostTypeEnumTest {
	@Test
	public void testEnumValues() {
		assertEquals(PostTypeEnum.ORIGINAL, PostTypeEnum.valueOf("ORIGINAL"));
		assertEquals(PostTypeEnum.REPLY, PostTypeEnum.valueOf("REPLY"));
	}

	@Test
	public void testEnumMethods() {
		assertEquals(true, PostTypeEnum.ORIGINAL.isValue());
		assertEquals(false, PostTypeEnum.REPLY.isValue());

		PostTypeEnum.ORIGINAL.setValue(false);
		assertEquals(false, PostTypeEnum.ORIGINAL.isValue());

		PostTypeEnum.REPLY.setValue(true);
		assertEquals(true, PostTypeEnum.REPLY.isValue());
	}

	@Test
	public void testEnumToString() {
		assertEquals("ORIGINAL", PostTypeEnum.ORIGINAL.toString());
	}
}
