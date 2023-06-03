package br.unb.scrap.model;

import java.io.Serializable;

import br.unb.scrap.enums.PostType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe model que representa um post,com seus respectivos atributos.
 * Getters/setters/hashcode and equals sendo gerados automaticamente e
 * implícitamente pelo lombok. Anotação @Entity indicando que é uma entidade do
 * banco de dados.
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
	private String authorName;

	/**
	 * Data do Post.
	 */
	private String publicationDate;

	/**
	 * Título do Post.
	 */
	@Size(max = 300)
	private String title;

	/**
	 * Texto presente no corpo do Post.
	 */
	@Column(columnDefinition = "LONGTEXT")
	private String body;

	/**
	 * Indica se a mensagem é original ou um reply.
	 */
	@Enumerated(EnumType.STRING)
	private PostType postType;

}
