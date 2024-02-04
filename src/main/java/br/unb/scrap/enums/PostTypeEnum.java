package br.unb.scrap.enums;

import lombok.Getter;

/**
 * Enum que representa os tipos de postagens: ORIGINAL e REPLY.
 */
@Getter
public enum PostTypeEnum {
    ORIGINAL(true), // Representa uma postagem original.
    REPLY(false);   // Representa uma resposta a uma postagem.

    private boolean value;

    PostTypeEnum(boolean value) {
        this.value = value;
    }

    /**
     * Define o valor do tipo de postagem.
     *
     * @param value o valor a ser definido
     */
    public void setValue(boolean value) {
        this.value = value;
    }
}
