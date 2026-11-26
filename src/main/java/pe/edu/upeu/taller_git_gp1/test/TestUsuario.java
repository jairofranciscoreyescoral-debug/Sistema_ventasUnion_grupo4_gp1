package pe.edu.upeu.taller_git_gp1.test;

import pe.edu.upeu.taller_git_gp1.implementacion.UsuarioDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IUsuarioDao;
import pe.edu.upeu.taller_git_gp1.modelo.Usuario;

public class TestUsuario {
    private static IUsuarioDao dao=new UsuarioDaoImpl();
    public void valid_login(){
        Usuario u= dao.login("admin","admin123");
        if (u!=null) {
            System.out.println("ID_usuario: "+u.getIdusuario());
            System.out.println("Nombres : "+u.getNombres());
            System.out.println("Apelllidos : "+u.getApellidos());
            System.out.println("Usuario: "+u.getUser());
            System.out.println("Tipo_User: "+u.getTipoUser());
        }else{
            System.out.println("Error :O");
            System.out.println("-----------------------------------------------");
        }
    }
    public void valid_insert(){

    }

    public static void main(String[] args) {
        TestUsuario t = new TestUsuario();
        t.valid_login();
    }
}
