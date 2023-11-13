package bd.fi.upm;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EJ2_25 {

	private Connection conn;
	public EJ2_25() {
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
			String url = "jdbc:mysql://" + serverAddress + "/" + db + "?noAccessToProcedureBodies=true";
			conn = DriverManager.getConnection(url, user, pass);
			conn.setAutoCommit(true);
			System.out.println("Conectado a la base de datos!");

			CallableStatement st = conn.prepareCall("CALL ADD_OSCAR(?,?,?)");
			st.setInt(1,2);
			st.setInt(2,3);
			st.setString(3, "Best actor");
			
			boolean result = st.execute();
			System.out.println("Registro añadido: " + result);
			
			st.close();
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
		new EJ2_25();
	}
}
