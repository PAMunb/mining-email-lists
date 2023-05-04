package unb.com.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String date;
	private String title;
	private String corpo;
	private String original;

	// private List<Post> replies;

}
