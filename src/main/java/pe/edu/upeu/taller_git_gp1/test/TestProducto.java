package pe.edu.upeu.taller_git_gp1.test;

import pe.edu.upeu.taller_git_gp1.implementacion.ProductoDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IProductoDao;
import pe.edu.upeu.taller_git_gp1.modelo.Producto;

import java.util.List;

public class TestProducto {
    static IProductoDao ha=new ProductoDaoImpl();
    public void listarProducto(){
        List<Producto> list = ha.listAll();
        if (list !=null && !list.isEmpty()) {
            for (Producto p:list){
                System.out.println("ID_PRODUCTO: "+p.getIdproducto());
                System.out.println("NOMBRE: "+p.getNombre());
                System.out.println("TIPO: "+p.getTipoProducto());
                System.out.println("PRECIO: "+p.getPrecio());
                System.out.println("ESTADO: "+p.getEstado());
                System.out.println("-----------------------------------------------");
            }
        }else{
            System.out.println("NO HAY REGISTRO");
            System.out.println("-----------------------------------------------");
        }
    }
    public static void main(String[] args) {
        TestProducto t=new TestProducto();
        t.listarProducto();
    }
}
