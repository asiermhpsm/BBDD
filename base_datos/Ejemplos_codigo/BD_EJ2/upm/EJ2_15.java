package bd.fi.upm;

public class EJ2_15 {

	public EJ2_15() {
		init();
	}


	private void init() {

			long msActuales = System.currentTimeMillis(); //tiempo actual en milisegundos
			java.sql.Date fechaEnSQLDate = new java.sql.Date(msActuales);
			java.sql.Time horaEnSQLDate = new java.sql.Time(msActuales);
			java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(msActuales);
			
			//otra forma sin usar System.currentTimeMillis y usando objetos Date de java.util
			java.sql.Date fechaEnSQLDate2 = new java.sql.Date(new java.util.Date().getTime());
			
			System.out.println(fechaEnSQLDate.toString());
			System.out.println(horaEnSQLDate.toString());
			System.out.println(sqlTimestamp.toString());
			
			System.out.println(fechaEnSQLDate2.toString());
			
	}

	public static void main(String args[]) {
		new EJ2_15();
	}
}
