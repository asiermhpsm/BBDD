package bd.fi.upm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EJ2_23 {

	private Connection conn;
	public EJ2_23() {
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

			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery("SELECT * FROM actor where actor_id >= 200");
			//para situarnos bien, cogemos los actores con ids entre >= 200
			// imprimo los nombres
			
			System.out.println("Nombres antes!:\n");
			while (rs.next()) {
				String id = rs.getString("actor_id");
				String name = rs.getString("first_name");
				System.out.println(id + ": " + name);
			}
			System.out.println();
			
			rs.moveToInsertRow(); // nos movemos en la posición para insertar
			rs.updateString("first_name", "Drew");
			rs.updateString("last_name", "Barrymore");
			rs.updateDate("last_update", new java.sql.Date(System.currentTimeMillis()));
			rs.insertRow();
			
			rs.beforeFirst(); // movemos el cursor al principio, justo antes del primer elemento

			System.out.println("Nombres después!:\n");
			while (rs.next()) {
				String id = rs.getString("actor_id");
				String name = rs.getString("first_name");
				System.out.println(id + ": " + name);
			}
			System.out.println();
			
			st.close();
			rs.close();
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
		new EJ2_23();
	}
}
