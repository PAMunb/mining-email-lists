package unb.com.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

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
@Table(name = "posts")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Id sendo gerado automaticamente com a anotação @GeneratedValue
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String name;
	private String date;
	private String title;
	private String corpo;
	private String original;
}
