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
		HashMap<Integer, Deposito> nuevo = new HashMap();
		String query = "SELECT * from depositos";
		Statement st;
		ResultSet rs;

		try {
			st = conn1.createStatement();
			rs = st.executeQuery(query);
			Deposito asdf;
			int indice = 0;
			while (rs.next()) {
				asdf = new Deposito(rs.getString("nombre"), rs.getInt("valor"), rs.getInt("cantidad"));
				nuevo.put(indice, asdf);
				indice++;

			}

			conn1.close();
			;
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

		try {
			st = conn1.createStatement();
			rs = st.executeQuery(query);
			Dispensador asdf;
			String indice = null;
			while (rs.next()) {
				asdf = new Dispensador(rs.getString("clave"), rs.getString("nombre"), rs.getInt("precio"),
						rs.getInt("cantidad"));
				nuevos.put(indice, asdf);
				indice = rs.getString("clave");

			}

			conn1.close();
			;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return nuevos;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = false;
		PreparedStatement ps;
		String query = "UPDATE `depositos` SET NOMBRE = ?, VALOR = ?, CANTIDAD = ?";
		for (int i = 0; i < depositos.size(); i++) {
			try {
				ps = (PreparedStatement) conn1.prepareStatement(query);
				ps.setString(1, depositos.get(i).getNombreMoneda());
				ps.setInt(2, depositos.get(i).getValor());
				ps.setInt(3, depositos.get(i).getCantidad());

				if (ps.executeUpdate() == 1) {
					todoOK = true;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = false;
		

		return todoOK;
	}

} // Fin de la clase