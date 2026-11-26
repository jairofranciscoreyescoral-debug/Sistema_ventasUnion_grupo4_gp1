package pe.edu.upeu.taller_git_gp1.test;

import pe.edu.upeu.taller_git_gp1.implementacion.VentaDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IVentaDao;
import pe.edu.upeu.taller_git_gp1.modelo.*;

import java.time.LocalDate;
import java.util.List;

public class TestVenta {
    static IVentaDao Dao = new VentaDaoImpl();

    public void insert() {
        Venta v = new Venta();
        Cliente c = new Cliente();
        Usuario u = new Usuario();

        c.setIdcliente(1);
        v.setCliente(c);

        u.setIdusuario(1);
        v.setUsuario(u);

        v.setFechaventa(LocalDate.of(2026, 11, 24));

        v.setTotal(200);

        boolean result = Dao.insert(v);
        if (result) {
            System.out.println("Venta success");
        } else {
            System.out.println("Venta error");
        }
    }

    public void list() {
        List<Venta> lista = Dao.listAll();
        if (lista != null && !lista.isEmpty()) {
            for (Venta v : lista) {
                System.out.println("ID-VENTA: " + v.getIdventa());
                System.out.println("FECHA_VENTA: " + v.getFechaventa());
                System.out.println("USUARIO: " + v.getUsuario());
                System.out.println("CLIENTE: " + v.getCliente());
                System.out.println("PRECIO: " + v.getTotal());
                System.out.println("-----------------------------------------------");
            }
        } else {

            System.out.println("NO HAY REGISTROS");
            System.out.println("-----------------------------------------------");
        }
    }

    public static void main(String[] args) {
        TestVenta t = new TestVenta();
        // t.insert();
        t.list();
    }
}
