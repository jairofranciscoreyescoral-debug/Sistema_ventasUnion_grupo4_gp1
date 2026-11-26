package pe.edu.upeu.taller_git_gp1.test;

import pe.edu.upeu.taller_git_gp1.implementacion.ClienteDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IClienteDao;
import pe.edu.upeu.taller_git_gp1.modelo.Cliente;

import java.util.List;

public class TestCliente {
    static IClienteDao cl=new ClienteDaoImpl();
    public void insertar(){
        Cliente c=new Cliente();
        c.setNombres("Juan");
        c.setApellidos("Perez");
        c.setDni("87654321");
        c.setEmail("juan@gmail.com");
        c.setGenero("Masculino");
        boolean result= cl.insert(c);
        if (result) {
            System.out.println("Success");
            System.out.println("-----------------------------------------------");
        }else{
            System.out.println("Error");
            System.out.println("-----------------------------------------------");
        }
    }

    public void delete(){
        Cliente c=new Cliente();
        c.setIdcliente(2);
        boolean result= cl.insert(c);
        if (result) {
            System.out.println("Success");
            System.out.println("-----------------------------------------------");
        }else{
            System.out.println("Error");
            System.out.println("-----------------------------------------------");
        }
    }
    public void update(){
        Cliente c=new Cliente();
        c.setNombres("Marco  ");
        c.setApellidos("Fabricio");
        c.setDni("10000000");
        c.setEmail("marco@gmail.com");
        c.setGenero("M");
        c.setIdcliente(1);
        boolean result= cl.update(c);
        if (result) {
            System.out.println("Success update");
            System.out.println("-----------------------------------------------");
        }else{
            System.out.println("Error");
            System.out.println("-----------------------------------------------");
        }
    }

    public void listarCliente(){
        List<Cliente> list =cl.listAll();
        if (list !=null && !list.isEmpty()) {
            for (Cliente c:list){
                System.out.println("ID_CLIENTE: "+c.getIdcliente());
                System.out.println("NOMBRES: "+c.getNombres());
                System.out.println("APELLIDOS: "+c.getApellidos());
                System.out.println("DNI: "+c.getDni());
                System.out.println("EMAIL: "+c.getEmail());
                System.out.println("GENERO: "+c.getGenero());
                System.out.println("-----------------------------------------------");
            }
        }else{
            System.out.println("NO HAY REGISTRO");
            System.out.println("-----------------------------------------------");
        }
    }
    public void searchById(){
        Cliente c=cl.searchById(1);
        if(c!=null){
            System.out.println("ID_CLIENTE: "+c.getIdcliente());
            System.out.println("NOMBRES: "+c.getNombres());
            System.out.println("APELLIDOS: "+c.getApellidos());
            System.out.println("DNI: "+c.getDni());
            System.out.println("EMAIL: "+c.getEmail());
            System.out.println("GENERO: "+c.getGenero());
            System.out.println("-----------------------------------------------");
        }else{
            System.out.println("No hay registros :v");
            System.out.println("-----------------------------------------------");
        }
    }
    public void searchByDni(){
        Cliente c=cl.searchByDni("70000000");
        if(c!=null){
            System.out.println("ID_CLIENTE: "+c.getIdcliente());
            System.out.println("NOMBRES: "+c.getNombres());
            System.out.println("APELLIDOS: "+c.getApellidos());
            System.out.println("DNI: "+c.getDni());
            System.out.println("EMAIL: "+c.getEmail());
            System.out.println("GENERO: "+c.getGenero());
            System.out.println("-----------------------------------------------");
        }else{
            System.out.println("No hay registros :v");
            System.out.println("-----------------------------------------------");
        }
    }

    public static void main(String[] args) {
        TestCliente t=new TestCliente();
        //t.insertar();
        //t.delete();
        //t.update();
        t.listarCliente();
        //t.searchById();
        t.searchByDni();
    }
}
