package bd.fi.upm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class EJ2_29 {

	private Connection conn;

	public EJ2_29() {
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
			String url = "jdbc:mysql://" + serverAddress + "/" + db
					+ "?noAccessToProcedureBodies=true";
			conn = DriverManager.getConnection(url, user, pass);
			conn.setAutoCommit(true);
			Statement stmt = null;
			stmt = conn.createStatement();
			stmt.execute("CREATE PROCEDURE `ADD_OSCAR`("
					+ "IN act_id INT,"
					+ "IN film_id INT,"
					+ "IN typePrize VARCHAR(45)) "
					+ "BEGIN "
					+ "START TRANSACTION; "
					+ "CREATE TABLE IF NOT EXISTS `sakila`.`oscar_winners` ("
					+ "  `actor_id` INT NOT NULL,"
					+ "`film_id` INT NOT NULL,"
					+ "`type_oscar` VARCHAR(45) NOT NULL,"
					+ "PRIMARY KEY (`actor_id`, `film_id`, `type_oscar`));"
					+ "SELECT @cnt:=COUNT(*) FROM film_actor where actor_id = act_id and film_id = film_id;"
					+ "IF @cnt >= 1 THEN "
					+ "	INSERT INTO oscar_winners(actor_id, film_id, type_oscar) VALUES (act_id, film_id, typePrize);"
					+ "END IF; " + "COMMIT;" + "END");
			System.out.println("Procedure created correctly!");
		} catch (Exception ex) {
			System.err.println("Exception: " + ex.getMessage());
		}
	}

	public static void main(String args[]) {
		new EJ2_29();
	}
}
