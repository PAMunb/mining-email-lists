package br.unb.scrap.domain;

import java.io.Serializable;

import br.unb.scrap.enums.PostTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe model representando um post.. Getters/setters/hashcode and equals
 * sendo gerados automaticamente e implícitamente pelo lombok.
 *
 */
@Getter
@Setter
@ToString
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
	@Column(length = 255)
	private String title;

	/**
	 * Texto presente no corpo do Post.
	 */
	@NotBlank
	@Column(columnDefinition = "TEXT")
	private String body;

	/**
	 * Indica se o post é original ou um reply.
	 */
	private boolean original;

	private PostTypeEnum postType;

	public void setPostType(PostTypeEnum postTypeEnum) {
		this.postType = postTypeEnum;

	}
}