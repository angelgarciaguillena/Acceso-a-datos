package tema4.ejercicio1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;


public class Ejercicio1 {
	
	static Scanner sc = new Scanner(System.in);
	 
	/*Creamos una variable para almacenar la conexion con la base de datos*/
	public static final String CONEXION = "jdbc:mysql://dns11036.phdns11.es:3306/ad2526_angel_garcia";
	
	/*Creamos una variable para almacenar el nombre de usuario*/
	public static final String USUARIO = "ad2526_angel_garcia";
	
	/*Creamos una variable para almacenar la contraseña*/
	public static final String CONTRASEÑA = "12345";
	
	public static void main(String[] args) {
		
		int opcion = 0;
		
		while(opcion != 7) {
			
			menu();
			opcion = sc.nextInt();
			
			switch(opcion) {
			case 1 ->{
				
			}
			
			case 2 ->{
				
			}
			
			case 3 ->{
				
			}
			
			case 4 ->{
				
			}
			
			case 5 ->{
				
			}
			
			case 6 ->{
				
			}
			
			case 7 ->{
				System.out.println("Has salido del programa");
			}
			
			default ->{
				System.out.println("opcion incorrecta");
			}
			}
			
		}
	}	
	
	public static void conectar() {
		
		try(Connection conexion = DriverManager.getConnection(CONEXION, USUARIO, CONTRASEÑA)){
			
			System.out.println("Conectado");
			
		} catch(SQLException e) {
			System.out.println("Error con la base de datos " + e.getMessage());
		}
	
	}
	
	private static void menu() {
		System.out.println("\n" + "Menu:");
		System.out.println("1. Crear Tablas");
		System.out.println("2. Insertar");
		System.out.println("3. Listar");
		System.out.println("4. Modificar");
		System.out.println("5. Borrar");
		System.out.println("6. Eliminar Tablas");
		System.out.println("7. Salir");
		System.out.println("Introduce una opcion");
	}
	/*
	public static String crearTablas(){
		
		String consulta;
		
		System.out.println("¿Quieres crear todas las tablas o una en concreto?");
		
		
	}
	
	public static void insertar() {
		
		System.out.println("Sobre que tabla quieres insertar?");
	}
	
	public static void listar() {
		
	}
	
	public static void modificar() {
		
	}
	
	public static void borrar() {
		
	}
	
	public static void eliminar() {
		
	}*/
}