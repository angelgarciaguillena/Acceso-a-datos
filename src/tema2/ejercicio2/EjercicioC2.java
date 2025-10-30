package tema2.ejercicio2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class EjercicioC2 {

    public static void main(String[] args) {

        String archivoLectura = "src\\tema2\\ejercicio2\\letras.txt";  
        String archivoEscritura = "src\\tema2\\ejercicio2\\letras2.txt";  
        String letrasConNumero = "src\\tema2\\ejercicio2\\letras3.txt";  

        try {

            File archivoLecturaFile = new File(archivoLectura);
            
            if (!archivoLecturaFile.exists()) {
                System.out.println("El archivo de lectura no existe. Se creará.");
                crearArchivo(archivoLectura);
            }

            escribirArchivoAleatorio(archivoLectura, archivoEscritura);
            escribirLetrasConNumero(archivoLectura, letrasConNumero);

        } catch (IOException e) {
            System.out.println("Error al procesar los archivos: " + e.getMessage());
        }
    }

    static void crearArchivo(String archivo) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.setLength(0);
            raf.writeBytes("abcde");
            System.out.println("Archivo de lectura creado con las letras 'a', 'b', 'c', 'd', 'e'.");
        }
    }

    static void escribirArchivoAleatorio(String archivoLectura, String archivoEscritura) throws IOException {
        try (RandomAccessFile rafLectura = new RandomAccessFile(archivoLectura, "r");
             RandomAccessFile rafEscritura = new RandomAccessFile(archivoEscritura, "rw")) {

            rafLectura.seek(0);  
            rafEscritura.setLength(0);  

            if (rafLectura.length() < 1) {
                System.out.println("El archivo de lectura está vacío.");
                return;
            }


            for (int i = 0; i < 5; i++) {
                rafLectura.seek(0); 
                rafEscritura.writeByte(rafLectura.readByte()); 
            }
        }
    }

    static void escribirLetrasConNumero(String archivoLectura, String archivoEscritura) throws IOException {
        try (RandomAccessFile rafLectura = new RandomAccessFile(archivoLectura, "r");
             FileWriter wr = new FileWriter(archivoEscritura)) {

            int length = (int) rafLectura.length();
            int num;
            char[] letras = new char[length];

            for (int i = 0; i < length; i++) {
                letras[i] = (char) rafLectura.readByte(); 
            }

            num = length;

            for (int i = length - 1; i >= 0; i--) {
                wr.write(letras[i] + "" + num + "\n"); 
                num--;
            }
        }
    }
}