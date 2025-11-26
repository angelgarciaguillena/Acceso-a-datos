package tema4.ejercicio1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ejercicio1 {

    static Scanner sc = new Scanner(System.in);

    public static final String CONEXION = "jdbc:mysql://dns11036.phdns11.es:3306/ad2526_angel_garcia";
    public static final String USUARIO = "ad2526_angel_garcia";
    public static final String CONTRASENA = "12345";

    static Connection conexion;

    public static void main(String[] args) {

        int opcion = 0;
        conectar();

        while (opcion != 7) {
            menu();
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> crearTablas();
                case 2 -> insertar();
                case 3 -> listar();
                case 4 -> modificar();
                case 5 -> borrar();
                case 6 -> eliminar();
                case 7 -> System.out.println("Has salido del programa");
                default -> System.out.println("Opción incorrecta");
            }
        }
    }

    public static void conectar() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(CONEXION, USUARIO, CONTRASENA);
            conexion = conn;
            System.out.println("Conectado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error con la base de datos: " + e.getMessage());
        }
    }

    private static void menu() {
        System.out.println("\nMenu:");
        System.out.println("1. Crear Tablas");
        System.out.println("2. Insertar");
        System.out.println("3. Listar");
        System.out.println("4. Modificar");
        System.out.println("5. Borrar");
        System.out.println("6. Eliminar Tablas");
        System.out.println("7. Salir");
        System.out.print("Introduce una opción: ");
    }

    public static String crearTablas() {

        int op = 0;
        String tabla = "";
        Statement st = null;

        System.out.println("¿Quieres crear todas las tablas o una en concreto?");
        System.out.println("1. Todas");
        System.out.println("2. Una concreta");
        System.out.print("Opción: ");

        op = sc.nextInt();
        sc.nextLine();

        try {
            st = conexion.createStatement();

            if (op == 1) {
                crearTablaPersona(st);
                crearTablaAlumno(st);
                crearTablaMatricula(st);
                return "Tablas creadas";
            }

            System.out.print("Nombre de tabla (Persona / Alumno / Matricula): ");
            tabla = sc.nextLine().toLowerCase();

            switch (tabla) {
                case "persona" -> crearTablaPersona(st);
                case "alumno" -> {
                    if (!existeTabla("Persona")) {
                        System.out.println("No puedes crear Alumno sin crear antes Persona.");
                        return null;
                    }
                    crearTablaAlumno(st);
                }
                case "matricula" -> {
                    if (!existeTabla("Alumno")) {
                        System.out.println("No puedes crear Matricula sin crear antes Alumno.");
                        return null;
                    }
                    crearTablaMatricula(st);
                }
                default -> System.out.println("Tabla no válida.");
            }

        } catch (SQLException e) {
            System.out.println("Error creando tablas: " + e.getMessage());
        }

        return null;
    }

    private static void crearTablaPersona(Statement st) throws SQLException {
        st.execute("""
                CREATE TABLE IF NOT EXISTS Persona(
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(60),
                    apellido VARCHAR(60),
                    edad INT
                );
                """);
        System.out.println("Tabla Persona creada.");
    }

    private static void crearTablaAlumno(Statement st) throws SQLException {
        st.execute("""
                CREATE TABLE IF NOT EXISTS Alumno(
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    idPersona INT,
                    fechaNacimiento DATE,
                    FOREIGN KEY (idPersona) REFERENCES Persona(id)
                );
                """);
        System.out.println("Tabla Alumno creada.");
    }

    private static void crearTablaMatricula(Statement st) throws SQLException {
        st.execute("""
                CREATE TABLE IF NOT EXISTS Matricula(
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    idAlumno INT,
                    curso VARCHAR(100),
                    FOREIGN KEY (idAlumno) REFERENCES Alumno(id)
                );
                """);
        System.out.println("Tabla Matricula creada.");
    }

    private static boolean existeTabla(String tabla) throws SQLException {
        DatabaseMetaData meta = null;
        ResultSet rs = null;

        meta = conexion.getMetaData();
        rs = meta.getTables(null, null, tabla, null);

        return rs.next();
    }

    public static void insertar() {

        int op = 0;

        System.out.println("¿Sobre qué tabla quieres insertar?");
        System.out.println("1. Persona");
        System.out.println("2. Alumno");
        System.out.println("3. Matricula");
        System.out.print("Opción: ");

        op = sc.nextInt();
        sc.nextLine();

        switch (op) {
            case 1 -> insertarPersona();
            case 2 -> insertarAlumno();
            case 3 -> insertarMatricula();
            default -> System.out.println("Tabla no válida.");
        }
    }

    private static void insertarPersona() {

        String nombre = "";
        String apellido = "";
        int edad = 0;
        PreparedStatement ps = null;

        try {
            System.out.print("Nombre: ");
            nombre = sc.nextLine();

            System.out.print("Apellido: ");
            apellido = sc.nextLine();

            System.out.print("Edad: ");
            edad = sc.nextInt();
            sc.nextLine();

            ps = conexion.prepareStatement(
                    "INSERT INTO Persona(nombre, apellido, edad) VALUES (?, ?, ?)");

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setInt(3, edad);

            ps.executeUpdate();
            System.out.println("Persona insertada.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void insertarAlumno() {

        String nombre = "";
        List<Integer> ids = null;
        int idPersona = 0;
        String fecha = "";
        PreparedStatement ps = null;

        System.out.print("Nombre de la Persona asociada: ");
        nombre = sc.nextLine();

        ids = buscarPersonaPorNombre(nombre);

        if (ids.isEmpty()) {
            System.out.println("No existe ninguna persona con ese nombre.");
            return;
        }

        idPersona = elegirID(ids, "persona");

        System.out.print("Fecha nacimiento (YYYY-MM-DD): ");
        fecha = sc.nextLine();

        try {
            ps = conexion.prepareStatement(
                    "INSERT INTO Alumno(idPersona, fechaNacimiento) VALUES (?, ?)");

            ps.setInt(1, idPersona);
            ps.setString(2, fecha);
            ps.executeUpdate();

            System.out.println("Alumno insertado.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void insertarMatricula() {

        String nombre = "";
        List<Integer> personas = null;
        int idPersona = 0;
        int idAlumno = 0;
        String curso = "";
        PreparedStatement ps = null;

        System.out.print("Nombre del alumno (persona asociada): ");
        nombre = sc.nextLine();

        personas = buscarPersonaPorNombre(nombre);

        if (personas.isEmpty()) {
            System.out.println("No existe persona con ese nombre.");
            return;
        }

        idPersona = elegirID(personas, "persona");

        idAlumno = buscarAlumnoPorPersona(idPersona);

        if (idAlumno == -1) {
            System.out.println("La persona existe, pero no es alumno.");
            return;
        }

        System.out.print("Curso: ");
        curso = sc.nextLine();

        try {
            ps = conexion.prepareStatement(
                    "INSERT INTO Matricula(idAlumno, curso) VALUES (?, ?)");

            ps.setInt(1, idAlumno);
            ps.setString(2, curso);
            ps.executeUpdate();

            System.out.println("Matrícula insertada.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void listar() {

        int op = 0;

        System.out.println("Listar:");
        System.out.println("1. Persona");
        System.out.println("2. Alumno");
        System.out.println("3. Matricula");
        System.out.print("Opción: ");

        op = sc.nextInt();
        sc.nextLine();

        switch (op) {
            case 1 -> listarPersona();
            case 2 -> listarAlumno();
            case 3 -> listarMatricula();
            default -> System.out.println("Opción no válida");
        }
    }

    private static void listarPersona() {

        String filtro = "";
        String sql = "";
        PreparedStatement ps = null;
        ResultSet rs = null;

        System.out.print("Filtrar por nombre (ENTER para no filtrar): ");
        filtro = sc.nextLine();

        sql = "SELECT * FROM Persona";
        
        if (!filtro.isEmpty()) {
        	sql += " WHERE nombre LIKE ?";
        }

        try {
            ps = conexion.prepareStatement(sql);

            if (!filtro.isEmpty()) {
            	ps.setString(1, "%" + filtro + "%");
            }
                

            rs = ps.executeQuery();

            while (rs.next()) {
                System.out.printf("[%d] %s %s (%d años)\n",
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listarAlumno() {

        String query = """
                SELECT A.id, P.nombre, P.apellido, A.fechaNacimiento
                FROM Alumno A
                JOIN Persona P ON A.idPersona = P.id
                """;

        ResultSet rs = null;

        try {
            rs = conexion.createStatement().executeQuery(query);

            while (rs.next()) {
                System.out.printf("[%d] %s %s - %s\n",
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listarMatricula() {

        String query = """
                SELECT M.id, P.nombre, P.apellido, M.curso
                FROM Matricula M
                JOIN Alumno A ON M.idAlumno = A.id
                JOIN Persona P ON A.idPersona = P.id
                """;

        ResultSet rs = null;

        try {
            rs = conexion.createStatement().executeQuery(query);

            while (rs.next()) {
                System.out.printf("[%d] %s %s - %s\n",
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void modificar() {

        int op = 0;

        System.out.println("Modificar:");
        System.out.println("1. Persona");
        System.out.print("Opción: ");

        op = sc.nextInt();
        sc.nextLine();

        if (op == 1) {
        	modificarPersona();
        }
    }

    private static void modificarPersona() {

        int id = 0;
        String nuevo = "";
        PreparedStatement ps = null;

        System.out.print("ID a modificar: ");
        id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nuevo nombre: ");
        nuevo = sc.nextLine();

        try {

            conexion.setAutoCommit(false);

            ps = conexion.prepareStatement(
                    "UPDATE Persona SET nombre=? WHERE id=?");

            ps.setString(1, nuevo);
            ps.setInt(2, id);
            ps.executeUpdate();

            listarPersona();

            System.out.print("¿Confirmar cambios? (s/n): ");

            if (sc.nextLine().equalsIgnoreCase("s")) {
                conexion.commit();
                System.out.println("Cambios confirmados.");
            } else {
                conexion.rollback();
                System.out.println("Cambios cancelados.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void borrar() {

        int id = 0;
        PreparedStatement ps = null;
        String confirmacion = "";

        System.out.print("ID de Persona a borrar: ");
        id = sc.nextInt();
        sc.nextLine();

        try {
            conexion.setAutoCommit(false);

            ps = conexion.prepareStatement(
                    "DELETE FROM Persona WHERE id=?");

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.print("¿Confirmar borrado? (s/n): ");
            confirmacion = sc.nextLine();

            if (confirmacion.equalsIgnoreCase("s")) {
                conexion.commit();
                System.out.println("Registro eliminado.");
            } else {
                conexion.rollback();
                System.out.println("Borrado cancelado.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void eliminar() {

        int op = 0;
        Statement st = null;
        String tabla = "";

        System.out.println("Eliminar tablas:");
        System.out.println("1. Todas");
        System.out.println("2. Una concreta");
        System.out.print("Opción: ");

        op = sc.nextInt();
        sc.nextLine();

        try {
            st = conexion.createStatement();

            if (op == 1) {
                st.execute("DROP TABLE IF EXISTS Matricula;");
                st.execute("DROP TABLE IF EXISTS Alumno;");
                st.execute("DROP TABLE IF EXISTS Persona;");
                System.out.println("Todas las tablas eliminadas.");
                return;
            }

            System.out.print("Tabla a eliminar: ");
            tabla = sc.nextLine().toLowerCase();

            switch (tabla) {
                case "matricula" -> st.execute("DROP TABLE IF EXISTS Matricula;");
                case "alumno" -> {
                    if (existeTabla("Matricula")) {
                        System.out.println("Debes eliminar Matricula antes.");
                        return;
                    }
                    st.execute("DROP TABLE IF EXISTS Alumno;");
                }
                case "persona" -> {
                    if (existeTabla("Alumno")) {
                        System.out.println("Debes eliminar Alumno antes.");
                        return;
                    }
                    st.execute("DROP TABLE IF EXISTS Persona;");
                }
                default -> System.out.println("Tabla no válida.");
            }

            System.out.println("Tabla eliminada.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<Integer> buscarPersonaPorNombre(String nombre) {

        List<Integer> lista = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(
                    "SELECT id FROM Persona WHERE nombre LIKE ?");
            ps.setString(1, "%" + nombre + "%");

            rs = ps.executeQuery();

            while (rs.next()) lista.add(rs.getInt(1));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }

    private static int elegirID(List<Integer> ids, String texto) {

        int seleccion = 0;

        System.out.println("Coincidencias de " + texto + ":");
        for (int id : ids) System.out.println("ID: " + id);

        System.out.print("Selecciona ID: ");
        seleccion = sc.nextInt();
        sc.nextLine();

        return seleccion;
    }

    private static int buscarAlumnoPorPersona(int idPersona) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(
                    "SELECT id FROM Alumno WHERE idPersona=?");

            ps.setInt(1, idPersona);

            rs = ps.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }
}
