package accesoDatos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.plaf.FileChooserUI;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {

	File fDis; // FicheroDispensadores
	File fDep; // FicheroDepositos

	public FicherosTexto() {
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositosCreados = new HashMap();
		Deposito nuevo = null;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader("Ficheros/datos/depositos.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = "";
		int i = 0;
		try {
			while ((line = br.readLine()) != null) {

				String[] datosaux = line.split(";");
				nuevo = new Deposito(datosaux[0].toString(), Integer.parseInt(datosaux[1].toString()),
						Integer.parseInt(datosaux[2].toString()));

				depositosCreados.put(i, nuevo);
				i++;

			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> dispensadoresCreados = new HashMap();
		Dispensador nuevo = null;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader("Ficheros/datos/dispensadores.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = "";
		int a = 0;
		String i = "aaaaa";
		try {
			while ((line = br.readLine()) != null) {

				String[] datosaux = line.split(";");

				nuevo = new Dispensador(datosaux[0].toString(), datosaux[1].toString(),
						Integer.parseInt(datosaux[2].toString()), Integer.parseInt(datosaux[3].toString()));
				dispensadoresCreados = new HashMap();
				i = i + String(a);
				dispensadoresCreados.put(i, nuevo);
				a++;

			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dispensadoresCreados;

	}

	private String String(int a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		boolean todoOK = false;
		try {
			fichero = new FileWriter("Ficheros/datos/depositos.txt");
			pw = new PrintWriter(fichero);

			for (int i = 0; i < depositos.size(); i++) {

				pw.println(depositos.get(i).getNombreMoneda() + ";" + depositos.get(i).getValor() + ";"
						+ depositos.get(i).getCantidad());

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (fichero != null) {
				pw.close();
				fichero.close();
				todoOK = true;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		FileWriter fichero = null;
		PrintWriter pw = null;
		boolean todoOK = false;
		try {
			fichero = new FileWriter("Ficheros/datos/dispensadores.txt");
			pw = new PrintWriter(fichero);

			for (int i = 0; i < dispensadores.size(); i++) {

				pw.println(dispensadores.get(i).getClave() + ";" + dispensadores.get(i).getNombreProducto() + ";"
						+ dispensadores.get(i).getPrecio() + ";" + dispensadores.get(i).getCantidad());

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (fichero != null) {
				pw.close();
				fichero.close();
				todoOK = true;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return todoOK;
	}

} // Fin de la clase