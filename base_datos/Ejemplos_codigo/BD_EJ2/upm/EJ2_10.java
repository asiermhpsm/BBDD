package bd.fi.upm;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class EJ2_10 {

	private Connection conn;
	public EJ2_10() {
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

			String query = "INSERT INTO actor (first_name, last_name, last_update) VALUES (?,?,?);";

			PreparedStatement pst1 = conn.prepareStatement(query);
			pst1.setString(1, "Luis");
			pst1.setString(2, "Tosar");
			pst1.setDate(3, new Date(new java.util.Date().getTime()));

			int result1 = pst1.executeUpdate();

			// La sentencia del DELETE va a dar un error por foreign key.
			// Esto implica que la transacción no se va a completar (se completa
			// al ejecutar el commit)
			// Con lo que la inserción anterior no se guarda en la base de datos
			// Comprobamos esto mirando en el workbench

			Statement st2 = conn.createStatement();
			int result2 = st2
					.executeUpdate("DELETE FROM actor where actor_id = '5'");

			conn.commit();

			System.out.println("Número de filas afectadas (R1): " + result1);
			System.out.println("Número de filas afectadas (R2): " + result2);
			System.out.println("Query ejecutada!");

			pst1.close();
			st2.close();
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
		new EJ2_10();
	}
}
