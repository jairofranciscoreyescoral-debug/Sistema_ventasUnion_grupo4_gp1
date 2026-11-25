package pe.edu.upeu.taller_git_gp1.implementacion;

import pe.edu.upeu.taller_git_gp1.interfaces.IVentaDao;
import pe.edu.upeu.taller_git_gp1.modelo.Venta;

import java.util.List;

public class VentaDaoImpl implements IVentaDao {
    @Override
    public boolean insert(Venta v) {
        return false;
    }

    @Override
    public boolean update(Venta v) {
        return false;
    }

    @Override
    public boolean delete(Venta v) {
        return false;
    }

    @Override
    public Venta searchById(int id) {
        return null;
    }

    @Override
    public List<Venta> listAll() {
        return List.of();
    }
}
