package pe.edu.upeu.taller_git_gp1.modelo;

import javafx.beans.property.*;

public class DetalleVenta {
    private final IntegerProperty iddetalleventa = new SimpleIntegerProperty();
    private final ObjectProperty<Venta> venta = new SimpleObjectProperty<>();
    private final ObjectProperty<Producto> producto = new SimpleObjectProperty<>();
    private final IntegerProperty cantidad = new SimpleIntegerProperty();
    private final DoubleProperty precio = new SimpleDoubleProperty();
    private final DoubleProperty subtotal = new SimpleDoubleProperty();

    public DetalleVenta() {
    }

    public int getIddetalleventa() {
        return iddetalleventa.get();
    }

    public void setIddetalleventa(int iddetalleventa) {
        this.iddetalleventa.set(iddetalleventa);
    }

    public IntegerProperty iddetalleventaProperty() {
        return iddetalleventa;
    }

    public Venta getVenta() {
        return venta.get();
    }

    public void setVenta(Venta venta) {
        this.venta.set(venta);
    }

    public ObjectProperty<Venta> ventaProperty() {
        return venta;
    }

    public Producto getProducto() {
        return producto.get();
    }

    public void setProducto(Producto producto) {
        this.producto.set(producto);
    }

    public ObjectProperty<Producto> productoProperty() {
        return producto;
    }

    public int getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(int cantidad) {
        this.cantidad.set(cantidad);
    }

    public IntegerProperty cantidadProperty() {
        return cantidad;
    }

    public double getPrecio() {
        return precio.get();
    }

    public void setPrecio(double precio) {
        this.precio.set(precio);
    }

    public DoubleProperty precioProperty() {
        return precio;
    }

    public double getSubtotal() {
        return subtotal.get();
    }

    public void setSubtotal(double subtotal) {
        this.subtotal.set(subtotal);
    }

    public DoubleProperty subtotalProperty() {
        return subtotal;
    }
}
