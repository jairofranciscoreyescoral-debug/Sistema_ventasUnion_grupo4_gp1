package pe.edu.upeu.taller_git_gp1.interfaces;

import pe.edu.upeu.taller_git_gp1.modelo.Producto;

import java.util.List;

public interface IProductoDao {
    public boolean insert(Producto p);
    public boolean update(Producto p);
    public boolean delete(Producto p);
    public Producto searchById(int id);
    public List<Producto> listAll();
}
