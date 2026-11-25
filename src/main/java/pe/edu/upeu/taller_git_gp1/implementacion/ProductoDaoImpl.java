package pe.edu.upeu.taller_git_gp1.implementacion;

import pe.edu.upeu.taller_git_gp1.interfaces.IProductoDao;
import pe.edu.upeu.taller_git_gp1.modelo.Producto;
import pe.edu.upeu.taller_git_gp1.utils.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDaoImpl implements IProductoDao {
    @Override
    public boolean insert(Producto p) {
        String sql = "INSERT INTO producto(nombre, tipoProducto, precio, stock, estado) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection cn = ConexionBD.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getTipoProducto());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getEstado());

            ps.executeUpdate();
            ps.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Producto p) {
        String sql = "UPDATE producto SET nombre=?, tipoProducto=?, precio=?, "
                + "stock=?, estado=? WHERE idproducto=?";
        try {
            Connection cn = ConexionBD.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getTipoProducto());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getEstado());
            ps.setInt(6, p.getIdproducto());

            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Producto p) {
        String sql = "DELETE FROM producto WHERE idproducto=?";
        try {
            Connection cn = ConexionBD.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, p.getIdproducto());

            ps.executeUpdate();
            ps.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Producto searchById(int id) {
        String sql = "SELECT * FROM producto WHERE idproducto=?";
        try {
            Connection cn = ConexionBD.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Producto p = null;
            if (rs.next()) {
                p = new Producto(
                        rs.getInt("idproducto"),
                        rs.getString("nombre"),
                        rs.getString("tipoProducto"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        rs.getString("estado")
                );
            }
            rs.close();
            ps.close();
            return p;
        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Producto> listAll() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try {
            Connection cn = ConexionBD.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto(
                        rs.getInt("idproducto"),
                        rs.getString("nombre"),
                        rs.getString("tipoProducto"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        rs.getString("estado")
                );
                lista.add(p);
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }
}
