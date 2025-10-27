package tema2.ejercicio1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EjercicioB {

	public static void main(String[] args) {
		
		String rutaBase = "C:/Users/angel.garcia/AD";
		String nombreAutor = "Angel Garcia";
		
		File carpetaBase = new File(rutaBase);
		
		if(carpetaBase.exists() && carpetaBase.isDirectory()) {
			
			recorrerCarpetas(carpetaBase, nombreAutor);
			
			System.out.println("Los archivos HTML han sido generados correctamente");
			
		} else {
			
			System.out.println("La ruta especificada no existe");
		}
	}
	
	public static void recorrerCarpetas(File carpeta, String autor) {
		
		crearArchivoHTML(carpeta, autor);
		
		File[] archivos = carpeta.listFiles();
		
		if(archivos != null) {
			
			for(File archivo : archivos) {
				
				if(archivo.isDirectory()) {
					
					recorrerCarpetas(archivo, autor);
				}
			}
		}
	}
	
	public static void crearArchivoHTML(File carpeta, String autor) {
		
		String nombreCarpeta = carpeta.getName();
		String ruta = carpeta.getAbsolutePath();
		File archivo = new File(carpeta, "index.html");
		
		try(FileWriter writer = new FileWriter(archivo)){
			
			writer.write("<html>" + "\n");
			writer.write("	<head>" + "\n");
			writer.write("		<title>" + nombreCarpeta + "</title>" + "\n");
			writer.write("	</head>" + "\n");
			writer.write("	<body>" + "\n");
			writer.write("		<h1>" + ruta + "\\index.html</h1>" + "\n");
			writer.write("		<h3>Autor: " + autor + "</h3>" + "\n");
			writer.write("	</body>" + "\n");
			writer.write("</html>" + "\n");
			
		} catch (IOException e) {
			
			System.out.println("No se ha podido crear el archivo en " + ruta);
		}
	}
}