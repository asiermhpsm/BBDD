package superheroes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Blob;

public class SuperheroesDatabase {

	private Connection conn;

	public SuperheroesDatabase() {
	}
	
	//Metodo que crea conexión con la base de datos. Devuelve false si la conexión es fallida
	public boolean openConnection() {
		boolean connect = false;
		if (conn == null) {
			String drv = "com.mysql.jdbc.Driver";
			try {
				Class.forName(drv);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			String serverAddress = "localhost:3306";
			String db = "superheroes";
			String user = "superheroes_user";
			String pass = "superheroes_pass";
			String url = "jdbc:mysql://" + serverAddress + "/" + db;
			try {
				conn = DriverManager.getConnection(url, user, pass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connect = true;
			System.out.println("Conectado a la base de datos!");
		}

		return connect;
	}

	//Método que cierra la conexión. Devuelbe false si falla
	public boolean closeConnection() {
		//Abrimos conexión para evitar un fallo
		openConnection();
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//Mediante un statement creamos una query mandamos una instrucción a la BD para crear la tabla.
	//Retorna false si falla
	public boolean createTableEscena() {
		openConnection();
		String query ="CREATE TABLE escena(" +
				" id_pelicula INT," +
				" n_orden INT, " +
				" titulo VARCHAR(100), " +
				" duracion INT," +
				" PRIMARY KEY(n_orden, id_pelicula), " +
				" FOREIGN KEY(id_pelicula) REFERENCES pelicula(id_pelicula)" +
				" ON DELETE CASCADE ON UPDATE CASCADE" +
				");";
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	//Mediante un statement creamos una query mandamos una instrucción a la BD para crear la tabla
	//Retorna false si falla
	public boolean createTableRival() {
		openConnection();
		String query =
				"CREATE TABLE rival(" +
						" id_sup INT," +
						" id_villano INT, " +
						" fecha_primer_encuentro DATE, " +
						" PRIMARY KEY(id_sup, id_villano), " +
						" FOREIGN KEY(id_sup) REFERENCES superheroe(id_sup)" +
						" ON DELETE CASCADE ON UPDATE CASCADE," +
						" FOREIGN KEY(id_villano) REFERENCES villano(id_villano)" +
						" ON DELETE CASCADE ON UPDATE CASCADE" +
						");";
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
			st.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	//Carga los datos de un CSV para que se añadan a la base datos
	public int loadEscenas(String fileName) {
		openConnection();
		int res=0;
		String query ="INSERT INTO escena(id_pelicula,n_orden, titulo, duracion)"+"VALUES(?,?,?,?);";
		BufferedReader br=null;
		try {
			br= new BufferedReader(new FileReader(fileName));
			String line =br.readLine();
			PreparedStatement pst= conn.prepareStatement(query);
			while(line!=null) {
				conn.setAutoCommit(false);
				String[] fila=line.split(";");
				
				//Si falla una iteración no propagamos el error
				try {
					pst.setInt(1, Integer.parseInt(fila[0]));
					pst.setInt(2, Integer.parseInt(fila[1]));
					pst.setString(3, fila[2]);
					pst.setInt(4, Integer.parseInt(fila[3]));
					res+=pst.executeUpdate();
				}catch(SQLException e){

				}

				conn.commit();
				line =br.readLine();
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	//Carga los datos de un CSV para que se añadan a la base datos
	public int loadProtagoniza(String fileName) {
		openConnection();
		int res=0;
		String query1 ="INSERT INTO protagoniza(id_sup, id_villano, id_pelicula) VALUES(?,?,?);";
		String query2 ="INSERT INTO rival(id_sup, id_villano) VALUES(?,?);";
		BufferedReader br=null;
		
		try {
			br= new BufferedReader(new FileReader(fileName));
			String line =br.readLine();
			PreparedStatement pst1= conn.prepareStatement(query1);
			PreparedStatement pst2= conn.prepareStatement(query2);
			conn.setAutoCommit(false);
			while(line!=null) {
				String[] fila=line.split(";");
				//Si falla una iteración los cambios de la transacción se eliminan
				try {
					pst1.setInt(1, Integer.parseInt(fila[0]));
					pst1.setInt(2, Integer.parseInt(fila[1]));
					pst1.setInt(3, Integer.parseInt(fila[2]));
					pst1.executeUpdate();
					res++;

					try {
						pst2.setInt(1, Integer.parseInt(fila[0]));
						pst2.setInt(2, Integer.parseInt(fila[1]));
						pst2.executeUpdate();
						res++;
					}catch(SQLException e){

					}

					line =br.readLine();
				}catch(SQLException e){
					res=0;
					conn.rollback();
					line=null;
				}
			}
			conn.commit();
			pst1.close();
			pst2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}


	//Muestra todos las peliculas disponibles
	public String catalogo() {
		openConnection();
		String res="{";
		//El resultado de la query es una lista de peliculas ordenada alfabéticamente
		String query="SELECT titulo FROM pelicula ORDER BY titulo ASC;";
		//Si algo falla se devuelve null, sino se crea el catálogo
		try {
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery(query);

			while(rs.next()) {
				String titulo=rs.getString(1);
				res=res+titulo+", ";
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			return null;
		}
		//Devolvemos en el formato pedido y evitamos errores de tamaño
		res=(res.equals("{"))? res:(res.substring(0, res.length()-2));
		return (res+"}");
	}

	//Devuelve la duracion de una pelicula
	public int duracionPelicula(String nombrePelicula) {
		openConnection();
		//Devuelve la pelicula con el título pedido
		String query1= "SELECT * FROM pelicula WHERE titulo=?";
		//Obtiene la duración de cada escena de esa pelicula
		String query2="SELECT e.duracion "
				+ "FROM escena e, pelicula p"
				+ " WHERE p.titulo= ? AND "
				+ "e.id_pelicula=p.id_pelicula; ";
		int res=0;
		//Por cada escena sumo su duración para obtener la duración total
		try {
			PreparedStatement pst1= conn.prepareStatement(query1);
			pst1.setString(1, nombrePelicula);
			ResultSet rs1=pst1.executeQuery();
			//Si la pelicula no existe se devuelve -1
			if(!rs1.next()) {
				rs1.close();
				pst1.close();
				return -1;
			}
			rs1.close();
			pst1.close();
			
			PreparedStatement pst2= conn.prepareStatement(query2);
			pst2.setString(1, nombrePelicula);
			ResultSet rs2=pst2.executeQuery();
			while(rs2.next()) {
				res+=rs2.getInt(1);
			}
			rs2.close();
			pst2.close();
			//Si ha habido un problema con sql devolvemos -2
		} catch (SQLException e) {
			return -2;
		}
		return res;
	}

	//Devuelve las escenas donde hay un villano
	public String getEscenas(String nombreVillano) {
		openConnection();
		//Se selcciona el titulo de la escena donde aparece el villano 
		//ordenado en primer lugar por el titulo de las peliculas y despues por las escenas
		String query1="SELECT e.titulo"
				+ " FROM protagoniza p, pelicula m, escena e, villano v"
				+ " WHERE e.id_pelicula = p.id_pelicula AND"
				+ " m.id_pelicula=e.id_pelicula AND"
				+ " m.id_pelicula =p.id_pelicula AND"
				+ " v.id_villano = p.id_villano AND"
				+ " v.nombre = ? "
				+ " GROUP BY e.titulo"
				+ " ORDER BY m.titulo ASC,e.n_orden ASC;";
		String res="{";
		//Si hay algún problema con sql devuelvo null
		try {
			PreparedStatement pst= conn.prepareStatement(query1);
			pst.setString(1, nombreVillano);
			ResultSet rs=pst.executeQuery();

			while(rs.next()) {
				String titulo=rs.getString(1);
				res=res+titulo+", ";
			}
			rs.close();
			pst.close();
		}catch (SQLException e) {
			return null;
		}
		//Devolvemos en el formato pedido y evitamos errores de tamaño
		res=(res.equals("{"))? res:(res.substring(0, res.length()-2));
		return (res+"}");
	}

	
	public boolean desenmascara(String nombre, String apellido, String filename) {
		openConnection();
		//Consigo el avatar de la persona real pedida
		String query ="SELECT s.avatar"
				+ "	FROM persona_real p, superheroe s"
				+ "	WHERE p.id_persona=s.id_persona AND"
				+ " p.nombre=? AND"
				+ " p.apellido=?;";
		//Guardo el avatar en el lugar indicado, si hay un error devuelvo false
		try {
			PreparedStatement pst= conn.prepareStatement(query);
			pst.setString(1, nombre);
			pst.setString(2, apellido);
			ResultSet rs=pst.executeQuery();
			
			byte data[]=null;
			Blob myBlob=null;
			
			//Si no hay avatar o el avatar es null, devuelve falso
			if(!rs.next()	||	rs.getBlob(1)==null)
				return false;
			
			myBlob = (Blob) rs.getBlob(1);
			data=myBlob.getBytes(1, (int)myBlob.length());
			FileOutputStream fos;
			fos = new FileOutputStream(filename);
			fos.write(data);
			fos.close();
			
			rs.close();
			pst.close();
		}catch (SQLException e) {
			return false;
		}catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}

}