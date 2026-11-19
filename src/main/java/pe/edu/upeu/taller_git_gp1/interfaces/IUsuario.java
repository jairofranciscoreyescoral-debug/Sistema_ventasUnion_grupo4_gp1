package pe.edu.upeu.taller_git_gp1.interfaces;

import pe.edu.upeu.taller_git_gp1.modelo.Usuario;

public interface IUsuario {
    public Usuario login(String user, String password);
    public boolean insert_user(Usuario usuario);
}
