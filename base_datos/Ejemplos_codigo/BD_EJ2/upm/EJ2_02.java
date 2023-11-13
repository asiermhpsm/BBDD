package bd.fi.upm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class EJ2_02 {

	public EJ2_02() {
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
		ResultSet rs = st.executeQuery("SELECT * FROM actor");
		System.out.println("Query ejecutada!");
		rs.close();
		st.close();
		conn.close();
	}

	public static void main(String args[]) {
		new EJ2_02();
	}
}
