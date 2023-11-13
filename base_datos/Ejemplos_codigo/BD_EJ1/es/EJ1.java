package Ejemplos_codigo.BD_EJ1.es;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;


public class EJ1 {

	public EJ1() {
		try {
			init();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("Clase no encontrada. ¿Se ha cargado el driver en el proyecto?");
			System.err.println("URL: http://dev.mysql.com/downloads/connector/j/");
		}
	}
	
	private void init() throws ClassNotFoundException {
		String drv = "com.mysql.jdbc.Driver";
		Class.forName(drv);
		System.out.println("Driver encontrado!");
	}
	
	public static void main(String[] args) {
		new EJ1();
	}

}
