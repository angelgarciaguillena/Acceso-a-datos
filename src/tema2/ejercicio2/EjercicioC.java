package tema2.ejercicio2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;

public class EjercicioC {

    public static void main(String[] args) {

        String archivoEntrada = "src\\tema2\\ejercicio2\\palabras_separadas.txt";
        String archivoSalida = "src\\tema2\\ejercicio2\\palabrasOrdenadas.txt";
        String palabra;
        ArrayList<String> listaPalabras = new ArrayList<>();

        try (RandomAccessFile rafLectura = new RandomAccessFile(archivoEntrada, "r"); RandomAccessFile rafEscritura = new RandomAccessFile(archivoSalida, "rw")) {
        	
            palabra = rafLectura.readLine();
            
            while (palabra != null) {
                listaPalabras.add(palabra.trim());
                palabra = rafLectura.readLine();
            }

            Collections.sort(listaPalabras);

            for (String p : listaPalabras) {
                rafEscritura.writeBytes(p + System.lineSeparator());
            }

            System.out.println("Archivo generado correctamente: " + archivoSalida);

        } catch (IOException e) {
            System.out.println("Error al procesar los archivos: " + e.getMessage());
        }
    }
}