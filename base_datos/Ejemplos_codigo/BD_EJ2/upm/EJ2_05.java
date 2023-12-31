package bd.fi.upm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class EJ2_05 {

	public EJ2_05() {
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
	
		Statement st = conn.createStatement();
		
		int result = st.executeUpdate("UPDATE actor SET first_name = 'Daniel' WHERE first_name = 'Dani'");
		
		System.out.println("N�mero de filas afectadas: " + result);
		System.out.println("Query ejecutada!");
		
		st.close();
		conn.close();
	}

	public static void main(String args[]) {
		new EJ2_05();
	}
}
