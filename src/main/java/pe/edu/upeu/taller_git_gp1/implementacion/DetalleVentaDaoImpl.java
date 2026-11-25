package pe.edu.upeu.taller_git_gp1.implementacion;

import pe.edu.upeu.taller_git_gp1.interfaces.IDetalleVentaDao;
import pe.edu.upeu.taller_git_gp1.modelo.DetalleVenta;
import pe.edu.upeu.taller_git_gp1.modelo.Producto;
import pe.edu.upeu.taller_git_gp1.modelo.Venta;
import pe.edu.upeu.taller_git_gp1.utils.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaDaoImpl implements IDetalleVentaDao {
    @Override
    public boolean insert(DetalleVenta d) {
        boolean flag = false;
        String query = "INSERT INTO detalleventa(ventaId, productoId, cantidad, precio, subtotal) "
                + "VALUES((SELECT MAX(idventa) FROM venta),?,?,?,?);";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);) {

            st.setInt(1, d.getProducto().getIdproducto());
            st.setInt(2, d.getCantidad());
            st.setDouble(3, d.getPrecio());
            st.setDouble(4, d.getSubtotal());

            st.executeUpdate();
            flag = true;

        } catch (SQLException e) {
            System.out.println("Error de insert DetalleVenta " + e.getMessage());
        }

        return flag;
    }

    @Override
    public boolean update(DetalleVenta d) {
        return false;
    }

    @Override
    public boolean delete(DetalleVenta d) {
        return false;
    }

    @Override
    public DetalleVenta searchById(int id) {
        DetalleVenta d = null;
        String query = "SELECT d.*, p.nombre, p.tipoProducto, p.stock, "
                + "p.estado, p.precio AS precioProducto "
                + "FROM detalleventa d, producto p "
                + "WHERE d.productoId = p.idproducto "
                + "AND d.iddetalleventa = ?";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Venta v = new Venta();
                    Producto p = new Producto();
                    d = new DetalleVenta();

                    v.setIdventa(rs.getInt("ventaId"));

                    p.setIdproducto(rs.getInt("productoId"));
                    p.setNombre(rs.getString("nombre"));
                    p.setTipoProducto(rs.getString("tipoProducto"));
                    p.setStock(rs.getInt("stock"));
                    p.setEstado(rs.getString("estado"));
                    p.setPrecio(rs.getDouble("precioProducto"));

                    d.setIddetalleventa(rs.getInt("iddetalleventa"));
                    d.setCantidad(rs.getInt("cantidad"));
                    d.setPrecio(rs.getDouble("precio"));
                    d.setSubtotal(rs.getDouble("subtotal"));

                    d.setVenta(v);
                    d.setProducto(p);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar Detalle Venta " + e.getMessage());
        }
        return d;
    }

    @Override
    public List<DetalleVenta> listAll() {
        List<DetalleVenta> lista = new ArrayList<>();
        String query = "SELECT d.*, p.nombre, p.tipoProducto, p.stock, "
                + "p.estado, p.precio AS precioProducto "
                + "FROM detalleventa d, producto p "
                + "WHERE d.productoId = p.idproducto";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
             ResultSet rs = st.executeQuery();) {

            while (rs.next()) {
                Venta v = new Venta();
                Producto p = new Producto();
                DetalleVenta d = new DetalleVenta();

                v.setIdventa(rs.getInt("ventaId"));

                p.setIdproducto(rs.getInt("productoId"));
                p.setNombre(rs.getString("nombre"));
                p.setTipoProducto(rs.getString("tipoProducto"));
                p.setStock(rs.getInt("stock"));
                p.setEstado(rs.getString("estado"));
                p.setPrecio(rs.getDouble("precioProducto"));

                d.setIddetalleventa(rs.getInt("iddetalleventa"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecio(rs.getDouble("precio"));
                d.setSubtotal(rs.getDouble("subtotal"));

                d.setVenta(v);
                d.setProducto(p);

                lista.add(d);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar Detalle Venta " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<DetalleVenta> listByVenta(int ventaId) {
        List<DetalleVenta> lista = new ArrayList<>();
        String query = "SELECT d.*, p.nombre, p.tipoProducto, p.stock, "
                + "p.estado, p.precio AS precioProducto "
                + "FROM detalleventa d, producto p "
                + "WHERE d.productoId = p.idproducto "
                + "AND d.ventaId = ?";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);) {

            st.setInt(1, ventaId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Venta v = new Venta();
                    Producto p = new Producto();
                    DetalleVenta d = new DetalleVenta();

                    v.setIdventa(rs.getInt("ventaId"));

                    p.setIdproducto(rs.getInt("productoId"));
                    p.setNombre(rs.getString("nombre"));
                    p.setTipoProducto(rs.getString("tipoProducto"));
                    p.setStock(rs.getInt("stock"));
                    p.setEstado(rs.getString("estado"));
                    p.setPrecio(rs.getDouble("precioProducto"));

                    d.setIddetalleventa(rs.getInt("iddetalleventa"));
                    d.setCantidad(rs.getInt("cantidad"));
                    d.setPrecio(rs.getDouble("precio"));
                    d.setSubtotal(rs.getDouble("subtotal"));

                    d.setVenta(v);
                    d.setProducto(p);

                    lista.add(d);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar Detalle Venta por Venta " + e.getMessage());
        }
        return lista;
    }
}
