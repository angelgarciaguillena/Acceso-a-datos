package tema4.ejercicio1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Ejercicio1 {
	 
	/*Creamos una variable para almacenar la conexion con la base de datos*/
	public static final String CONEXION = "jdbc:mysql://dns11036.phdns11.es:3306/ad2526_angel_garcia";
	
	/*Creamos una variable para almacenar el nombre de usuario*/
	public static final String USUARIO = "angel_garcia";
	
	/*Creamos una variable para almacenar la contraseña*/
	public static final String CONTRASEÑA = "12345";
	
	public static void main(String[] args) {
		
		/*Creamos un try catch para avisar al usuario en caso de que se produzca un error*/
		try(Connection conexion = DriverManager.getConnection(CONEXION, USUARIO, CONTRASEÑA)){
			
			System.out.println("Conectado");
			
		} catch(SQLException e) {
			System.out.println("Error con la base de datos " + e.getMessage());
		}
	}
}