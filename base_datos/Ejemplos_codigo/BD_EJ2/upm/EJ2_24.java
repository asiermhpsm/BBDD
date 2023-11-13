package bd.fi.upm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EJ2_24 {

	private Connection conn;
	public EJ2_24() {
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
			ResultSet rs = st.executeQuery("SELECT * FROM rental where rental_id >= 40 and rental_id <= 50;");
			//para situarnos bien, cogemos las rentals con ids entre 40 y 50
			// imprimo los valores
			
			System.out.println("Rentals antes!:\n");
			while (rs.next()) {
				String id = rs.getString("rental_id");
				String customer = rs.getString("customer_id");
				System.out.println(id + ": " + customer);
			}
			System.out.println();
			
			rs.absolute(4); // nos movemos a la fila que queremos borrar
			rs.deleteRow();

			rs.beforeFirst(); // movemos el cursor al principio, justo antes del primer elemento

			System.out.println("Rentals después!:\n");
			while (rs.next()) {
				String id = rs.getString("rental_id");
				String customer = rs.getString("customer_id");
				System.out.println(id + ": " + customer);
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
		new EJ2_24();
	}
}
