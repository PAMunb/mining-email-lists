package unb.com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.transaction.annotation.Transactional;

import unb.com.entities.Post;

public class PostDAO {

	private Connection connection;

	public PostDAO(Connection connection) {
		this.connection = connection;
	}

	@Transactional
	public void insert(Post post) throws SQLException {
		String query = "INSERT INTO posts (id, name, date, title, body, original) VALUES (?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, post.getId());
		statement.setString(2, post.getName());
		statement.setString(3, post.getDate());
		statement.setString(4, post.getTitle());
		statement.setString(5, post.getBody());
		statement.setString(6, post.getOriginal());

		statement.executeUpdate();
	}

	public void testeInsertPost() {
		Post post = new Post();

		post.setId("teste");
		post.setName("teste");
		post.setDate("teste");
		post.setTitle("teste");
		post.setBody("teste");
		post.setOriginal("teste");

	}
}
