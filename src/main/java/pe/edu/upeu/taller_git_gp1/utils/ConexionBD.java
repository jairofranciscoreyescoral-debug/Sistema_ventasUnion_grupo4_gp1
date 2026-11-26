package pe.edu.upeu.taller_git_gp1.utils;

import java.io.File;
import java.sql.*;

public class ConexionBD {
    private static Connection connection;
    /*
    public static Connection getConnection(){
        try{
            if (connection==null||connection.isClosed()){
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:C:/sqlite/db/union_gp1.db";
                connection = DriverManager.getConnection(url);
                connection.setAutoCommit(true);

                try(Statement st = connection.createStatement()){
                    st.execute("PRAGMA foreign_keys = ON;");
                }
                System.out.println("Conexion existosa a SQLite");
            }
            return connection;

        } catch (Exception e) {
            throw new RuntimeException("Error de conexion a la BD "+e.getMessage());
        }
    }
    */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // 1. Obtener la carpeta del usuario
                String userHome = System.getProperty("user.home");
                File directory = new File(userHome, "SistemaVentasUnion");

                // 2. Crear la carpeta si no existe
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // 3. Ruta de la base de datos
                String dbPath = new File(directory, "union_gp1.db").getAbsolutePath();
                String url = "jdbc:sqlite:" + dbPath;

                // 4. Cargar el controlador SQLite
                Class.forName("org.sqlite.JDBC");

                // 5. Crear la conexión
                connection = DriverManager.getConnection(url);
                connection.setAutoCommit(true);

                // 6. Activar claves foráneas
                try (Statement st = connection.createStatement()) {
                    st.execute("PRAGMA foreign_keys = ON;");
                }

                // 7. Crear las tablas e insertar el administrador
                initDatabase();

                System.out.println("Conexión exitosa a SQLite: " + dbPath);
            }

            return connection;

        } catch (Exception e) {
            throw new RuntimeException("Error de conexión a la BD: " + e.getMessage(), e);
        }
    }

    private static void initDatabase() {
        String sqlCliente = "CREATE TABLE IF NOT EXISTS cliente (" +
                "idcliente INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombres TEXT, apellidos TEXT, dni TEXT UNIQUE, email TEXT, genero TEXT);";

        String sqlUsuario = "CREATE TABLE IF NOT EXISTS usuario (" +
                "idusuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user TEXT UNIQUE, password TEXT, tipoUser TEXT, " +
                "nombres TEXT, apellidos TEXT, dni TEXT);";

        String sqlProducto = "CREATE TABLE IF NOT EXISTS producto (" +
                "idproducto INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, tipoProducto TEXT, precio REAL, stock INTEGER, estado TEXT);";

        String sqlVenta = "CREATE TABLE IF NOT EXISTS venta (" +
                "idventa INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "clienteId INTEGER NOT NULL, fechaventa TEXT NOT NULL, " +
                "total REAL NOT NULL, usuarioId INTEGER NOT NULL, " +
                "FOREIGN KEY(clienteId) REFERENCES cliente(idcliente), " +
                "FOREIGN KEY(usuarioId) REFERENCES usuario(idusuario));";

        String sqlDetalleVenta = "CREATE TABLE IF NOT EXISTS detalleventa (" +
                "iddetalleventa INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ventaId INTEGER NOT NULL, productoId INTEGER NOT NULL, " +
                "cantidad INTEGER NOT NULL, precio REAL NOT NULL, subtotal REAL NOT NULL, " +
                "FOREIGN KEY(ventaId) REFERENCES venta(idventa), " +
                "FOREIGN KEY(productoId) REFERENCES producto(idproducto));";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlCliente);
            stmt.execute(sqlUsuario);
            stmt.execute(sqlProducto);
            stmt.execute(sqlVenta);
            stmt.execute(sqlDetalleVenta);

            // Insertar administrador si todavía no existe
            String sqlAdmin = "INSERT OR IGNORE INTO usuario " +
                    "(user, password, tipoUser, nombres, apellidos) " +
                    "VALUES ('admin', 'admin123', 'ADMIN', 'Francisco', 'Sistema');";

            stmt.executeUpdate(sqlAdmin);

            System.out.println("Base de datos inicializada correctamente.");

        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar las tablas: " + e.getMessage(), e);
        }
    }
}
