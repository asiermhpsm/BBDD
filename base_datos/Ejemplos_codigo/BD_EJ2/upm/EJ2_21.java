package bd.fi.upm;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EJ2_21 {

	private Connection conn;
	public EJ2_21() {
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

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT picture FROM actor WHERE first_name = 'Megan' and last_name = 'Fox';");
			
			byte data[] = null;
			Blob myBlob = null;
			
			while (rs.next()) {
				myBlob = rs.getBlob("picture");
				data = myBlob.getBytes(1, (int)myBlob.length());
			}

			FileOutputStream fos = new FileOutputStream("pics/meganfox_downloaded.jpg");
			fos.write(data);
			fos.close();
			System.out.println("Fichero guardado!");
			
			st.close();
			rs.close();
			conn.close();
		} catch (SQLException esql) {
			System.err.println("Mensaje: " + esql.getMessage());
			System.err.println("Código: " + esql.getErrorCode());
			System.err.println("Estado SQL: " + esql.getSQLState());
		} catch (ClassNotFoundException e) {
			System.err.println("Error al cargar el driver.");
		} catch (FileNotFoundException e) {
			System.err.println("Fichero no encontrado!");
		} catch (IOException e) {
			System.err.println("Error escribiendo el fichero: " + e.getMessage());
		}
	}

	public static void main(String args[]) {
		new EJ2_21();
	}
}
