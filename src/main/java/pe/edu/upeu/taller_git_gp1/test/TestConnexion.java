package pe.edu.upeu.taller_git_gp1.test;

import pe.edu.upeu.taller_git_gp1.utils.ConexionBD;

import java.sql.Connection;

public class TestConnexion {
    public static void testConexion(){
        try{
            Connection conn = ConexionBD.getConnection();
            if (conn !=null && !conn.isClosed()){
                System.out.println("Conexion Success!!!");
            }else{
                System.out.println("Error Conexion!!!");
            }

        } catch (Exception e) {
            System.out.println("Error de conexion "+e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testConexion();
    }
}
