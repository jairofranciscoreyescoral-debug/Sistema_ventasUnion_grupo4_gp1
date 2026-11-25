package pe.edu.upeu.taller_git_gp1.implementacion;

import pe.edu.upeu.taller_git_gp1.interfaces.IVentaDao;
import pe.edu.upeu.taller_git_gp1.modelo.Cliente;
import pe.edu.upeu.taller_git_gp1.modelo.Usuario;
import pe.edu.upeu.taller_git_gp1.modelo.Venta;
import pe.edu.upeu.taller_git_gp1.utils.ConexionBD;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentaDaoImpl implements IVentaDao {
    @Override
    public boolean insert(Venta v) {
        boolean flag = false;
        String consulta = "INSERT INTO venta(clienteId, fechaventa, total, usuarioId) VALUES(?,?,?,?)";
        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(consulta)) {
            st.setInt(1, v.getCliente().getIdcliente());
            st.setString(2, v.getFechaventa().toString());
            st.setDouble(3, v.getTotal());
            st.setInt(4, v.getUsuario().getIdusuario());
            st.executeUpdate();

            String queryId = "SELECT last_insert_rowid()";
            try (PreparedStatement stId = cn.prepareStatement(queryId);
                 ResultSet rs = stId.executeQuery()) {
                if (rs.next()) {
                    v.setIdventa(rs.getInt(1));
                }
            }
            flag = true;
        } catch (Exception e) {
            System.out.println("Error al registrar venta: " + e.getMessage());
        }
        return flag;
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
        Venta v = null;
        String query = "SELECT v.*, c.nombres, c.apellidos, c.dni, "
                + "u.user, u.nombres AS nombresUsuario, "
                + "u.apellidos AS apellidosUsuario "
                + "FROM venta v, cliente c, usuario u "
                + "WHERE v.clienteId = c.idcliente "
                + "AND v.usuarioId = u.idusuario "
                + "AND v.idventa = ?";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Cliente cl = new Cliente();
                    Usuario u = new Usuario();
                    v = new Venta();

                    cl.setIdcliente(rs.getInt("clienteId"));
                    cl.setNombres(rs.getString("nombres"));
                    cl.setApellidos(rs.getString("apellidos"));
                    cl.setDni(rs.getString("dni"));

                    u.setIdusuario(rs.getInt("usuarioId"));
                    u.setUser(rs.getString("user"));
                    u.setNombres(rs.getString("nombresUsuario"));
                    u.setApellidos(rs.getString("apellidosUsuario"));

                    v.setIdventa(rs.getInt("idventa"));
                    v.setFechaventa(LocalDate.parse(rs.getString("fechaventa")));
                    v.setTotal(rs.getDouble("total"));
                    v.setCliente(cl);
                    v.setUsuario(u);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar Venta " + e.getMessage());
        }
        return v;
    }

    @Override
    public List<Venta> listAll() {
        List<Venta> lista = new ArrayList<>();
        String query = "SELECT v.*, c.nombres, c.apellidos, c.dni, "
                + "u.user, u.nombres AS nombresUsuario, "
                + "u.apellidos AS apellidosUsuario "
                + "FROM venta v, cliente c, usuario u "
                + "WHERE v.clienteId = c.idcliente "
                + "AND v.usuarioId = u.idusuario";

        try (Connection cn = ConexionBD.getConnection();
             PreparedStatement st = cn.prepareStatement(query);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Cliente cl = new Cliente();
                Usuario u = new Usuario();
                Venta v = new Venta();

                cl.setIdcliente(rs.getInt("clienteId"));
                cl.setNombres(rs.getString("nombres"));
                cl.setApellidos(rs.getString("apellidos"));
                cl.setDni(rs.getString("dni"));

                u.setIdusuario(rs.getInt("usuarioId"));
                u.setUser(rs.getString("user"));
                u.setNombres(rs.getString("nombresUsuario"));
                u.setApellidos(rs.getString("apellidosUsuario"));

                v.setIdventa(rs.getInt("idventa"));
                v.setFechaventa(LocalDate.parse(rs.getString("fechaventa")));
                v.setTotal(rs.getDouble("total"));
                v.setCliente(cl);
                v.setUsuario(u);

                lista.add(v);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Venta " + e.getMessage());
        }
        return lista;
    }
}
