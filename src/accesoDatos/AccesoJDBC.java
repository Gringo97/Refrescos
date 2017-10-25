package accesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import com.mysql.jdbc.PreparedStatement;

import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");

		try {
			HashMap<String, String> datosConexion;

			LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();

			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;

			Class.forName(driver);
			conn1 = DriverManager.getConnection(urlbd, user, password);
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			}

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
			// e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("ERROR: No se ha podido conectar con la base de datos");
			System.out.println(e.getMessage());
			// e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> nuevo = new HashMap<Integer, Deposito>();
		String query = "SELECT * from depositos";
		Statement st;
		ResultSet rs;
		Deposito aux;
		try {
			st = conn1.createStatement();
			rs = st.executeQuery(query);
			Deposito asdf;
			int indice = 0;
			while (rs.next()) {
				indice = rs.getInt("valor");
				aux = new Deposito(rs.getString("nombre"), rs.getInt("valor"), rs.getInt("cantidad"));
				nuevo.put(indice, aux);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return nuevo;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> nuevos = new HashMap();
		String query = "SELECT * from depositos";
		Statement st;
		ResultSet rs;
		Dispensador asdf;

		try {
			st = conn1.createStatement();
			rs = st.executeQuery(query);
			String indice = null;
			while (rs.next()) {
				indice = rs.getString("clave");
				asdf = new Dispensador(rs.getString("clave"), rs.getString("nombre"), rs.getInt("precio"),
						rs.getInt("cantidad"));
				nuevos.put(indice, asdf);

			}

			;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return nuevos;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = false;
		java.sql.PreparedStatement ps;

		for (Integer key : depositos.keySet()) {
			Deposito value = depositos.get(key);
			String query = "UPDATE `depositos` SET cantidad=? WHERE  valor=" + key;

			try {
				ps = conn1.prepareStatement(query);
				ps.setInt(1, value.getCantidad());
				ps.executeUpdate();

				if (ps.executeUpdate() == 1) {
					todoOK = true;
				}

			} catch (SQLException e) {
				todoOK = false;
				e.printStackTrace();

			}

		}

		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = false;
		java.sql.PreparedStatement ps;
		String query;

		for (String key : dispensadores.keySet()) {
			Dispensador value = dispensadores.get(key);
			query = "UPDATE `dispensadores` SET `cantidad`=? WHERE dispensadores.clave='" + key + "'";
			try {
				ps = conn1.prepareStatement(query);
				ps.setInt(1, value.getCantidad());
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}

			
		}
		return todoOK;
	}
} // Fin de la clase