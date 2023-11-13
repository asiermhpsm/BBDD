package bd.fi.upm;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class EJ2_09 {

	public EJ2_09() {
		try {
			init();
		} catch (Exception e) {
			System.err.println("Error al conectar a la BD: " + e.getMessage());
		}
	}

	private void init() throws Exception {
		String drv = "com.mysql.jdbc.Driver";
		Class.forName(drv);

		String serverAddress = "localhost:3306";
		String db = "sakila";
		String user = "bd";
		String pass = "bdupm";
		String url = "jdbc:mysql://" + serverAddress + "/" + db;
		Connection conn = DriverManager.getConnection(url, user, pass);
		System.out.println("Conectado a la base de datos!");
	
		conn.setAutoCommit(false);
		
		String query = "INSERT INTO actor (first_name, last_name, last_update) VALUES (?,?,?);";
		
		PreparedStatement pst1 = conn.prepareStatement(query);
		pst1.setString(1, "Luis");
		pst1.setString(2, "Tosar");
		pst1.setDate(3, new Date(new java.util.Date().getTime()));
		
		pst1.executeUpdate();
		pst1.close();
		conn.close();
	}

	public static void main(String args[]) {
		new EJ2_09();
	}
}
