package pe.edu.upeu.taller_git_gp1.interfaces;

import pe.edu.upeu.taller_git_gp1.modelo.DetalleVenta;

import java.util.List;

public interface IDetalleVentaDao {
    public boolean insert(DetalleVenta d);

    public boolean update(DetalleVenta d);

    public boolean delete(DetalleVenta d);

    public DetalleVenta searchById(int id);

    public List<DetalleVenta> listAll();

    public List<DetalleVenta> listByVenta(int ventaId);
}
