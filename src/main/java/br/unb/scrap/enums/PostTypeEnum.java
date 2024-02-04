package br.unb.scrap.enums;

import lombok.Getter;

/**
 * Enum que representa os tipos de postagens: ORIGINAL e REPLY.
 */
@Getter
public enum PostTypeEnum {
	ORIGINAL(true), REPLY(false);

	private boolean value;

	PostTypeEnum(boolean value) {
		this.value = value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
}

//public enum PostTypeEnum {
//
//	ORIGINAL(true), REPLY(false);
//
//	public boolean isValue() {
//		return value;
//	}
//
//	public void setValue(boolean value) {
//		this.value = value;
//	}
//
//	private boolean value;
//}