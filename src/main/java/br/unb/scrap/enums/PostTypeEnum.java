package br.unb.scrap.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Classe enum que representa os tipos de postagens. O enum possui dois valores
 * possíveis: ORIGINAL e REPLY.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public enum PostTypeEnum {

	ORIGINAL(true), REPLY(false);

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	private boolean value;
}