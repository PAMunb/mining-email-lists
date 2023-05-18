package br.unb.scrap.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe model com seus respectivos atributos. Getters/setters/hashcode and
 * equals sendo gerados automaticamente e implícitamente pelo lombok. Anotação
 * indicando que é uma entidade do banco de dados.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador único do Post sendo gerado automaticamente com a
	 * anotação @GeneratedValue
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Nome do Autor do Post.
	 */
	private String name;

	/**
	 * Data do Post.
	 */
	private String date;

	/**
	 * Título do Post.
	 */
	private String title;

	/**
	 * Texto presente no corpo do Post.
	 */
	private String body;

	/**
	 * Indica se a mensagem é original ou um reply.
	 */
	private boolean original;

}
