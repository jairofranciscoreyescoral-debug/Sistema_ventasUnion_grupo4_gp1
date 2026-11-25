package pe.edu.upeu.taller_git_gp1.interfaces;

import pe.edu.upeu.taller_git_gp1.modelo.Venta;

import java.util.List;

public interface IVentaDao {
    public boolean insert(Venta v);
    public boolean update(Venta v);
    public boolean delete(Venta v);
    public Venta searchById(int id);
    public List<Venta> listAll();
}
