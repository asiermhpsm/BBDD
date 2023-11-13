package bd.fi.upm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EJ2_17 {

	private Connection conn;
	public EJ2_17() {
		init();
	}

	private void init() {
		try {
			String drv = "com.mysql.jdbc.Driver";
			Class.forName(drv);

			String serverAddress = "localhost:3306";
			String db = "sakila";
			String user = "bd";
			String pass = "bdupm";
			String url = "jdbc:mysql://" + serverAddress + "/" + db;
			conn = DriverManager.getConnection(url, user, pass);
			conn.setAutoCommit(true);
			System.out.println("Conectado a la base de datos!");

			String query = "INSERT INTO actor (first_name, last_name, last_update) VALUES (?,?,?);";
			PreparedStatement pst = conn.prepareStatement(query);
			
			pst.setString(1, "Charlize");
			pst.setString(2, "Theron");
			pst.setDate(3, new java.sql.Date(System.currentTimeMillis()));
			
			pst.executeUpdate();
			
			System.out.println("Añadido!");
			conn.close();
		} catch (SQLException esql) {
			System.err.println("Mensaje: " + esql.getMessage());
			System.err.println("Código: " + esql.getErrorCode());
			System.err.println("Estado SQL: " + esql.getSQLState());
		} catch (ClassNotFoundException e) {
			System.err.println("Error al cargar el driver.");
		}
	}

	public static void main(String args[]) {
		new EJ2_17();
	}
}
