package bd.fi.upm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EJ2_28 {

	public EJ2_28() {
		try {
			ArrayList<ActorCSV> nombresActores = getNombresActores();
			BufferedWriter bW = new BufferedWriter(new FileWriter("actores.csv"));
			for (int i = 0; i < nombresActores.size(); i++) {
				ActorCSV act = nombresActores.get(i);
				bW.write(act.toCSV());
				bW.newLine();
			}
			bW.close();
		} catch (Exception e) {
			System.err.println("Error al conectar a la BD: " + e.getMessage());
		}
	}

	private ArrayList<ActorCSV> getNombresActores() throws Exception {
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

		ArrayList<ActorCSV> ret = new ArrayList<ActorCSV>();
		while (rs.next()) {
			int id = rs.getInt("actor_id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString(3); // tercera columna. Empiezan en					// 1, no en 0
			Date lastUpdate = rs.getDate("last_update");
			ret.add(new ActorCSV(id, firstName, lastName, lastUpdate));
		}
		rs.close();
		st.close();
		conn.close();

		return ret;
	}

	public static void main(String args[]) {
		new EJ2_28();
	}
}

class ActorCSV {
	private int id;
	private String firstName;
	private String lastName;
	private Date lastUpdate;

	public ActorCSV(int id, String fn, String ln, Date lu) {
		this.id = id;
		this.firstName = fn;
		this.lastName = ln;
		this.lastUpdate = lu;
	}

	public String toCSV() {
		return + id + "," + firstName + "," + lastName + "," + lastUpdate.toString();
	}
}
