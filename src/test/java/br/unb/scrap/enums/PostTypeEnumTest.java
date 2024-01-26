package br.unb.scrap.enums;

public class PostTypeEnumTest {

	public static void main(String[] args) {
		testOriginalPostType();
		testReplyPostType();
	}

	private static void testOriginalPostType() {
		PostTypeEnum originalType = PostTypeEnum.ORIGINAL;
		boolean value = originalType.isValue();
		System.out.println("This is the original email" + " >>>>>>" + value);
	}

	private static void testReplyPostType() {
		PostTypeEnum replyType = PostTypeEnum.REPLY;
		boolean value = replyType.isValue();
		System.out.println("This is the reply email" + " >>>>>>" + value);
	}
}
