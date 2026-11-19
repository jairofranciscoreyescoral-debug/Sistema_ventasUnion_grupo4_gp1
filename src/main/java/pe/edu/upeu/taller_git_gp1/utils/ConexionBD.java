package pe.edu.upeu.taller_git_gp1.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConexionBD {
    private static Connection connection;
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
}
