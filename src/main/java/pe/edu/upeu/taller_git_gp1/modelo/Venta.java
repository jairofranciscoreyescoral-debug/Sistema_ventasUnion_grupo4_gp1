package pe.edu.upeu.taller_git_gp1.modelo;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Venta {
    private final IntegerProperty idventa = new SimpleIntegerProperty();
    private final ObjectProperty<Cliente> cliente = new SimpleObjectProperty<>();
    private final ObjectProperty<Usuario> usuario = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> fechaventa = new SimpleObjectProperty<>();
    private final DoubleProperty total = new SimpleDoubleProperty();

    public Venta() {
    }

    public int getIdventa() {
        return idventa.get();
    }

    public void setIdventa(int idventa) {
        this.idventa.set(idventa);
    }

    public IntegerProperty idventaProperty() {
        return idventa;
    }

    public Cliente getCliente() {
        return cliente.get();
    }

    public void setCliente(Cliente cliente) {
        this.cliente.set(cliente);
    }

    public ObjectProperty<Cliente> clienteProperty() {
        return cliente;
    }

    public Usuario getUsuario() {
        return usuario.get();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario.set(usuario);
    }

    public ObjectProperty<Usuario> usuarioProperty() {
        return usuario;
    }

    public LocalDate getFechaventa() {
        return fechaventa.get();
    }

    public void setFechaventa(LocalDate fechaventa) {
        this.fechaventa.set(fechaventa);
    }

    public ObjectProperty<LocalDate> fechaventaProperty() {
        return fechaventa;
    }

    public double getTotal() {
        return total.get();
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public DoubleProperty totalProperty() {
        return total;
    }
}
