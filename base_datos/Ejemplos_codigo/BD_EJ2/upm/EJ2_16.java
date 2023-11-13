package bd.fi.upm;
import java.util.Calendar;

public class EJ2_16 {

	public EJ2_16() {
		init();
	}

	private void init() {

		Calendar now = Calendar.getInstance();

		java.util.Date dateHoy = now.getTime();

		System.out.println("Date from Calendar: " + dateHoy.toString());

		long msActuales = dateHoy.getTime();

		java.sql.Date fechaEnSQLDate = new java.sql.Date(msActuales);
		java.sql.Time horaEnSQLDate = new java.sql.Time(msActuales);
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(msActuales);

		System.out.println(fechaEnSQLDate.toString());
		System.out.println(horaEnSQLDate.toString());
		System.out.println(sqlTimestamp.toString());

	}

	public static void main(String args[]) {
		new EJ2_16();
	}
}
