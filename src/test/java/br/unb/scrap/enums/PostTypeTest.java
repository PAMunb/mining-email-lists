package br.unb.scrap.enums;

public class PostTypeTest {

	public static void main(String[] args) {
		testOriginalPostType();
		testReplyPostType();
	}

	private static void testOriginalPostType() {
		PostType originalType = PostType.ORIGINAL;
		boolean value = originalType.isValue();
		System.out.println("This is the original email" + " >>>>>>" + value);
	}

	private static void testReplyPostType() {
		PostType replyType = PostType.REPLY;
		boolean value = replyType.isValue();
		System.out.println("This is the reply email" + " >>>>>>" + value);
	}
}
