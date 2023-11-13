package bd.fi.upm;
import java.util.Calendar;


public class EJ2_19 {

	public EJ2_19() {
		init();
	}


	private void init() {

			Calendar cal1 = Calendar.getInstance();
			cal1.set(Calendar.YEAR, 2016);
			cal1.set(Calendar.MONTH, Calendar.JUNE);
			cal1.set(Calendar.DAY_OF_MONTH, 12);
			
			Calendar cal2 = Calendar.getInstance();
			cal2.set(Calendar.YEAR, 2014);
			cal2.set(Calendar.MONTH, Calendar.DECEMBER);
			cal2.set(Calendar.DAY_OF_MONTH, 26);
			
			java.sql.Timestamp t1 = new java.sql.Timestamp(cal1.getTime().getTime());
			java.sql.Timestamp t2 = new java.sql.Timestamp(cal2.getTime().getTime());
			
			int compare = t1.compareTo(t2);
			
			System.out.println("T1: " + t1.toString());
			System.out.println("T2: " + t2.toString());
			if (compare == 0) {
				System.out.println("Timestamp iguales");
			}
			if (compare > 0) {
				System.out.println("T1 es despues de T2");
			}
			if (compare < 0) {
				System.out.println("T1 es antes de T2");
			}
	}

	public static void main(String args[]) {
		new EJ2_19();
	}
}
