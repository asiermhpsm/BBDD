package bd.fi.upm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EJ2_22 {

	private Connection conn;
	public EJ2_22() {
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

			String query = "SELECT * FROM actor where actor_id >= 40 and actor_id <= 50;";
			//PreparedStatement pst = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery(query);
			//para situarnos bien, cogemos los actores con ids entre 40 y 50 (no hay ningún Morgan)
			// imprimo los nombres
			
			System.out.println("Nombres antes!:\n");
			while (rs.next()) {
				String name = rs.getString("first_name");
				System.out.println(name);
			}
			System.out.println();
			
			rs.beforeFirst();
			
			rs.absolute(2); //nos movemos a la fila 2 (2º resultado)
			rs.updateString(2, "Morgan"); // actualizamos la columna 2 (first_name) de ese registro
			rs.updateRow(); // actualizamos el registro
			rs.beforeFirst(); // movemos el cursor al principio, justo antes del primer elemento

			System.out.println("Nombres después!:\n");
			while (rs.next()) {
				String name = rs.getString("first_name");
				System.out.println(name);
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
		new EJ2_22();
	}
}
