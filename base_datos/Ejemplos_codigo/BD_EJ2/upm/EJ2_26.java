package bd.fi.upm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EJ2_26 {

	public EJ2_26() {
		try {
			ArrayList<String> nombresActores = getNombresActores();
			for (int i = 0; i < nombresActores.size(); i++) {
				String n = nombresActores.get(i);
				System.out.println("Actor[" + i + "]: " + n);
			}
		} catch (Exception e) {
			System.err.println("Error al conectar a la BD: " + e.getMessage());
		}
	}

	private ArrayList<String> getNombresActores() throws Exception {
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
		
		ArrayList<String> ret = new ArrayList<String>();
		while (rs.next()) {
			String firstName = rs.getString("first_name");
			ret.add(firstName);
		}
		rs.close();
		st.close();
		conn.close();
		
		return ret;
	}

	public static void main(String args[]) {
		new EJ2_26();
	}
}
