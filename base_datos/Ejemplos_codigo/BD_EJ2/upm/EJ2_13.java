package bd.fi.upm;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EJ2_13 {

	private Connection conn;
	public EJ2_13() {
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

			DatabaseMetaData dbMet = conn.getMetaData();
			
			System.out.println("Tipo de BD: " + dbMet.getDatabaseProductName());
			System.out.println("Versión: " + dbMet.getDatabaseProductVersion());
			System.out.println("Información del driver:");
			System.out.println("\tNombre: " + dbMet.getDriverName());
			System.out.println("\tVersión: " + dbMet.getDriverVersion());
			
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
		new EJ2_13();
	}
}
