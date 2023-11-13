package bd.fi.upm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class EJ2_14 {

	private Connection conn;
	public EJ2_14() {
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

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM actor");
 			ResultSetMetaData rsmd = rs.getMetaData();
 
			System.out.println("Numero de columnas: " + rsmd.getColumnCount());
			
			for (int i = 1; i < rsmd.getColumnCount(); i++) {
				System.out.println("Columna " + i + ": " + rsmd.getColumnName(i));
				System.out.println("Etiqueta " + i + ": " + rsmd.getColumnLabel(i));
				System.out.println("Tipo de columna " + i + ": " + rsmd.getColumnTypeName(i));
			}
			
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
		new EJ2_14();
	}
}
