package bd.fi.upm;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EJ2_12 {

	private Connection conn;
	public EJ2_12() {
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
			System.out.println("Conectado a la base de datos!");

			conn.setAutoCommit(false);

			// Insertamos un actor nuevo
			
			String query = "INSERT INTO actor (first_name, last_name, last_update) VALUES (?,?,?);";

			PreparedStatement pst1 = conn.prepareStatement(query);
			pst1.setString(1, "Luis");
			pst1.setString(2, "Tosar");
			pst1.setDate(3, new Date(new java.util.Date().getTime()));

			int result1 = pst1.executeUpdate();

			Statement st2 = conn.createStatement();
			ResultSet rs2 = st2.executeQuery("SELECT * from actor where last_name = 'Tosar'");
			while (rs2.next()) {
				int id = rs2.getInt("actor_id");
				String firstName = rs2.getString("first_name");
				String lastName = rs2.getString("last_name");
				Date lastUpdate = rs2.getDate("last_update");
				
				System.out.println("Actor:");
				System.out.println("\tID: " + id);
				System.out.println("\tName: " + firstName);
				System.out.println("\tLast name: " + lastName);
				System.out.println("\tLast update: " + lastUpdate.toString());
			}

			conn.commit(); // hacemos un commit antes del delete. Esto hará que la transacción anterior
			// se ejecute y el delete forme parte de otra diferente
			
			Statement st3 = conn.createStatement();
			int result3 = st3
					.executeUpdate("DELETE FROM actor where actor_id = '5'");

			System.out.println("Número de filas afectadas (R1): " + result1);
			System.out.println("Número de filas afectadas (R3): " + result3);
			System.out.println("Query ejecutada!");

			pst1.close();
			st2.close();
			st3.close();
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
		new EJ2_12();
	}
}
