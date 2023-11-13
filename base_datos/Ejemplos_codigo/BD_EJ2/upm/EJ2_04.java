package bd.fi.upm;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class EJ2_04 {

	public EJ2_04() {
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

		String nombres[] = { "Clara", "Dani", "Edward", "Denzel" };
		String apellidos[] = { "Lago", "Rovira", "Norton", "Washington" };
		
		// No hace falta pasar ID porque es autoincremental
		String query = "INSERT INTO actor (first_name, last_name, last_update) VALUES (?,?,?);";
		
		PreparedStatement pst = conn.prepareStatement(query);
		for (int i = 0; i < nombres.length; i++) {
			pst.setString(1, nombres[i]);
			pst.setString(2, apellidos[i]);
			pst.setDate(3, new Date(new java.util.Date().getTime()));
			int res = pst.executeUpdate();
			System.out.println("Insertado correctamente? " + ((res == 1)?"Si":"No"));
		}
		System.out.println("Query ejecutada!");
		
		pst.close();
		conn.close();
	}

	public static void main(String args[]) {
		new EJ2_04();
	}
}
