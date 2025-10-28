package tema2.ejercicio1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EjercicioA {

	public static void main(String[] args) {
		
		String rutaFichero = "src\\tema2\\ejercicio1\\carpetas.txt";
		String rutaBase = "C:\\Users\\angel.garcia\\";
		String linea;
		boolean creado;
		
		try(BufferedReader br = new BufferedReader(new FileReader(rutaFichero))){
			
			linea = br.readLine();
			
			while(linea != null) {
				
				File nuevaCarpeta = new File(rutaBase, linea);
				
				if(!nuevaCarpeta.exists()) {
					
					creado = nuevaCarpeta.mkdir();
					
					if(creado) {
						System.out.println("La carpeta " + nuevaCarpeta.getAbsolutePath() + " ha sido creada");
						
					} else {
						System.out.println("No se ha podido crear la carpeta " + nuevaCarpeta.getAbsolutePath());
					}
					
				} else {
					System.out.println("La carpeta " + nuevaCarpeta.getAbsolutePath() + " ya existe");
				}
			}
			
			br.close();
			
		} catch (IOException e) {
			System.err.println("Error: No se puede leer el fichero " + e.getMessage());
		}
	}
}