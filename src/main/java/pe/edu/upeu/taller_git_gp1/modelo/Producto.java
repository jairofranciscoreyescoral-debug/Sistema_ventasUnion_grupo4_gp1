package pe.edu.upeu.taller_git_gp1.modelo;

import javafx.beans.property.*;

public class Producto {
    private final IntegerProperty idproducto = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty tipoProducto = new SimpleStringProperty();
    private final DoubleProperty precio = new SimpleDoubleProperty();
    private final IntegerProperty stock = new SimpleIntegerProperty();
    private final StringProperty estado = new SimpleStringProperty();

    public Producto() {
    }

    public Producto(int idproducto, String nombre, String tipoProducto, Double precio, int stock, String estado) {
        setIdproducto(idproducto);
        setNombre(nombre);
        setTipoProducto(tipoProducto);
        setPrecio(precio);
        setStock(stock);
        setEstado(estado);
    }

    public final String getEstado() {
        return estado.get();
    }

    public final void setEstado(String value) {
        estado.set(value);
    }

    public final StringProperty estadoProperty() {
        return estado;
    }

    public final int getStock() {
        return stock.get();
    }

    public final void setStock(int value) {
        stock.set(value);
    }

    public final IntegerProperty stockProperty() {
        return stock;
    }

    public final double getPrecio() {
        return precio.get();
    }

    public final void setPrecio(double value) {
        precio.set(value);
    }

    public final DoubleProperty precioProperty() {
        return precio;
    }

    public final String getTipoProducto() {
        return tipoProducto.get();
    }

    public final void setTipoProducto(String value) {
        tipoProducto.set(value);
    }

    public final StringProperty tipoProductoProperty() {
        return tipoProducto;
    }


    public final String getNombre() {
        return nombre.get();
    }

    public final void setNombre(String value) {
        nombre.set(value);
    }

    public final StringProperty nombreProperty() {
        return nombre;
    }


    public final int getIdproducto() {
        return idproducto.get();
    }

    public final void setIdproducto(int value) {
        idproducto.set(value);
    }

    public final IntegerProperty idproductoProperty() {
        return idproducto;
    }
    @Override
    public String toString() {
        return this.getNombre() != null ? this.getNombre() + " - S/. " + this.getPrecio() : "";
    }
}
