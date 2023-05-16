package unb.com.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe model com seus respectivos atributos. Getters/setters/hashcode and
 * equals sendo gerados automaticamente e implícitamente pelo lombok. Anotação
 * indicando que é uma entidade do bando de dados.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonPropertyOrder({ "id", "name", "date", "title", "body", "original" })
@Table(name = "posts")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Id sendo gerado automaticamente com a anotação @GeneratedValue
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "date", nullable = false)
	private String date;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "body", nullable = false)
	private String body;

	@Column(name = "original", nullable = false)
	private String original;
}
