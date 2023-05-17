package br.unb.scrap.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Classe model com seus respectivos atributos. Getters/setters/hashcode and
 * equals sendo gerados automaticamente e implícitamente pelo lombok. Anotação
 * indicando que é uma entidade do bando de dados.
 *
 */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
//@JsonPropertyOrder({ "id", "name", "date", "title", "body", "original" })
//@Table(name = "posts")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Id sendo gerado automaticamente com a anotação @GeneratedValue
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "id", nullable = false)
	private Long id;

//	@Column(name = "name", nullable = false, length = 255)
	private String name;

//	@Column(name = "date", nullable = false)
	private String date;

//	@Column(name = "title", nullable = false)
	private String title;

//	@Column(name = "body", nullable = false)
	private String body;

//	@Column(name = "original", nullable = false)
	private String original;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	@Override
	public String toString() {
		return String.format("Post [id=%s, name=%s, date=%s, title=%s, body=%s, original=%s]", id, name, date, title, body, original);
	}
	
	
}
