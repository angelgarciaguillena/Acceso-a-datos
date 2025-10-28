package tema2.ejercicio2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EjercicioB {
	
	public static void main(String[] args) {

        String archivoEntrada = "src\\tema2\\ejercicio2\\palabras.txt";
        String archivoSalida = "src\\tema2\\ejercicio2\\palabras_separadas.txt";
        String linea = "";
        String texto = "";
        char c;

        try (BufferedReader br = new BufferedReader(new FileReader(archivoEntrada)); BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))) {

            linea = br.readLine();

            while (linea != null) {
                texto += linea.trim();
                linea = br.readLine();
            }

            for (int i = 0; i < texto.length(); i++) {
            	
                c = texto.charAt(i);
                
                if (Character.isUpperCase(c) && i != 0) {
                    bw.newLine(); 
                }
                
                bw.write(c);
            }

            System.out.println("Archivo generado correctamente: " + archivoSalida);

        } catch (IOException e) {
            System.out.println("Error al procesar los archivos: " + e.getMessage());
        }
    }
}