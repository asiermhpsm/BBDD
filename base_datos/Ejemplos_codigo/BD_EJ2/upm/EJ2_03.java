package bd.fi.upm;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class EJ2_03 {

	public EJ2_03() {
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
		String user = "bd3";
		String pass = "bdupm";
		String url = "jdbc:mysql://" + serverAddress + "/" + db;
		Connection conn = DriverManager.getConnection(url, user, pass);
		System.out.println("Conectado a la base de datos!");

		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM actor");
		System.out.println("Query ejecutada!");
		
		while (rs.next()) {
			int id = rs.getInt("actor_id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString(3); // tercera columna. Empiezan en 1, no en 0
			Date lastUpdate = rs.getDate("last_update");
			System.out.println("Actor:");
			System.out.println("\tID: " + id);
			System.out.println("\tName: " + firstName);
			System.out.println("\tLast name: " + lastName);
			System.out.println("\tLast update: " + lastUpdate.toString());
		}
		rs.close();
		st.close();
		conn.close();
	}

	public static void main(String args[]) {
		new EJ2_03();
	}
}
