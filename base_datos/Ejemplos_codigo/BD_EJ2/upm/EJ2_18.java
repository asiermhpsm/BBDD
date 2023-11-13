package bd.fi.upm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

public class EJ2_18 {

	private Connection conn;
	public EJ2_18() {
		init();
	}

	@SuppressWarnings("deprecation")
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

			String query = "INSERT INTO actor (first_name, last_name, last_update) VALUES (?,?,?);";
			PreparedStatement pst = conn.prepareStatement(query);
			
			// con Date (deprecated)
			java.util.Date fechaUtil = new java.util.Date("2013/01/15");
			java.sql.Date fecha = new java.sql.Date(fechaUtil.getTime());
			
			// con Calendar
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2013);
			cal.set(Calendar.MONTH, Calendar.MAY);
			cal.set(Calendar.DAY_OF_MONTH, 15);
			// fecha = new java.sql.Date(cal.getTime().getTime()); //descomentar esta línea para usar Calendar
			
			pst.setString(1, "Scarlett");
			pst.setString(2, "Johansson");
			pst.setDate(3, fecha);
			
			pst.executeUpdate();
			
			System.out.println("Añadido!");
			
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
		new EJ2_18();
	}
}
