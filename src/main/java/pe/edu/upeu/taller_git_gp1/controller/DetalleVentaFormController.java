package pe.edu.upeu.taller_git_gp1.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import pe.edu.upeu.taller_git_gp1.implementacion.ProductoDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IProductoDao;
import pe.edu.upeu.taller_git_gp1.modelo.DetalleVenta;
import pe.edu.upeu.taller_git_gp1.modelo.Producto;
import pe.edu.upeu.taller_git_gp1.utils.AlertsUtils;

import java.sql.SQLException;

public class DetalleVentaFormController {
    private static final AlertsUtils alertUtils = new AlertsUtils();

    @FXML
    private TextField txtBuscar, txtCantidad;

    @FXML
    private Label lblSubtotal;

    @FXML
    private TableView<Producto> tableProducto;

    @FXML
    private TableColumn<Producto, Number> ColNumero;

    @FXML
    private TableColumn<Producto, Integer> ColId;

    @FXML
    private TableColumn<Producto, String> ColNombre;

    @FXML
    private TableColumn<Producto, Double> ColPrecio;

    @FXML
    private TableColumn<Producto, Integer> ColStock;

    @FXML
    private TableView<DetalleVenta> tableDetalle;

    @FXML
    private TableColumn<DetalleVenta, String> ColDetalleProducto;

    @FXML
    private TableColumn<DetalleVenta, Integer> ColDetalleCantidad;

    @FXML
    private TableColumn<DetalleVenta, Double> ColDetallePrecio;

    @FXML
    private TableColumn<DetalleVenta, Double> ColDetalleSubtotal;

    private final IProductoDao productoDao = new ProductoDaoImpl();

    private ObservableList<Producto> listaProducto =
            FXCollections.observableArrayList();

    private ObservableList<Producto> productosFiltrados =
            FXCollections.observableArrayList();

    private ObservableList<DetalleVenta> listaDetalle =
            FXCollections.observableArrayList();

    private Producto productoEncontrado;

    private VentaFormController ventaFormController;

    private boolean guardado = false;

    @FXML
    private void initialize() {

        // Columnas de productos
        ColNumero.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });

        ColId.setCellValueFactory(
                cd -> cd.getValue().idproductoProperty().asObject());

        ColNombre.setCellValueFactory(
                cd -> cd.getValue().nombreProperty());

        ColPrecio.setCellValueFactory(
                cd -> cd.getValue().precioProperty().asObject());

        ColStock.setCellValueFactory(
                cd -> cd.getValue().stockProperty().asObject());

        // Columnas de detalles
        ColDetalleProducto.setCellValueFactory(
                cd -> cd.getValue().getProducto().nombreProperty());

        ColDetalleCantidad.setCellValueFactory(
                cd -> cd.getValue().cantidadProperty().asObject());

        ColDetallePrecio.setCellValueFactory(
                cd -> cd.getValue().precioProperty().asObject());

        ColDetalleSubtotal.setCellValueFactory(
                cd -> cd.getValue().subtotalProperty().asObject());

        tableProducto.setItems(listaProducto);
        tableDetalle.setItems(listaDetalle);

        cargarDatos();

        lblSubtotal.setText("0.00");
        txtCantidad.clear();
    }

    private void cargarDatos() {
        listaProducto.setAll(productoDao.listAll());
    }

    @FXML
    private void buscarProducto() {

        String nombre = txtBuscar.getText().toLowerCase();

        productosFiltrados.clear();

        for (Producto producto : listaProducto) {
            if (producto.getNombre().toLowerCase().contains(nombre)) {
                productosFiltrados.add(producto);
            }
        }

        tableProducto.setItems(productosFiltrados);
    }

    @FXML
    private void calcularsubtotalTotal() {
        productoEncontrado = tableProducto.getSelectionModel().getSelectedItem();
        if (productoEncontrado == null || txtCantidad.getText().isEmpty()) {
            lblSubtotal.setText("0.00");
            return;
        }

        try {
            int cantidad = Integer.parseInt(txtCantidad.getText());

            if (cantidad > 0) {
                double subtotal = cantidad * productoEncontrado.getPrecio();
                lblSubtotal.setText(String.format("%.2f", subtotal));
            } else {
                lblSubtotal.setText("0.00");
            }

        } catch (NumberFormatException e) {
            lblSubtotal.setText("0.00");
        }
    }

    @FXML
    private void handleAgregar() {
        productoEncontrado = tableProducto.getSelectionModel().getSelectedItem();

        if (productoEncontrado == null || txtCantidad.getText().isEmpty()) {
            Alert alert = alertUtils.viewAlerts(
                    "Error",
                    "Seleccione un producto e ingrese la cantidad",
                    "WARNING"
            );
            alert.showAndWait();
            return;
        }

        int cantidad;

        try {
            cantidad = Integer.parseInt(txtCantidad.getText());
        } catch (Exception e) {
            Alert alert = alertUtils.viewAlerts(
                    "Error",
                    "Ingrese una cantidad válida",
                    "WARNING"
            );
            alert.showAndWait();
            return;
        }

        if (cantidad <= 0) {
            Alert alert = alertUtils.viewAlerts(
                    "Error",
                    "La cantidad debe ser mayor que cero",
                    "WARNING"
            );
            alert.showAndWait();
            return;
        }

        if (cantidad > productoEncontrado.getStock()) {
            Alert alert = alertUtils.viewAlerts(
                    "Error",
                    "No hay suficiente stock",
                    "WARNING"
            );
            alert.showAndWait();
            return;
        }

        calcularsubtotalTotal();

        DetalleVenta detalle = new DetalleVenta();

        detalle.setProducto(productoEncontrado);
        detalle.setCantidad(cantidad);
        detalle.setPrecio(productoEncontrado.getPrecio());
        detalle.setSubtotal(cantidad * productoEncontrado.getPrecio());

        listaDetalle.add(detalle);

        productoEncontrado = null;
        tableProducto.getSelectionModel().clearSelection();

        txtCantidad.clear();
        lblSubtotal.setText("0.00");
    }

    @FXML
    private void handleGuardar() {

        if (listaDetalle.isEmpty()) {
            Alert alert = alertUtils.viewAlerts(
                    "Error",
                    "Agregue al menos un producto",
                    "WARNING"
            );
            alert.showAndWait();
            return;
        }

        ventaFormController.recibirDetalles(listaDetalle);

        guardado = true;
        cerrar();
    }

    public void setVentaFormController(VentaFormController ventaFormController) {
        this.ventaFormController = ventaFormController;
    }

    private void cerrar() {
        ((Stage) txtBuscar.getScene().getWindow()).close();
    }

    @FXML
    private void handleCancelar() {
        cerrar();
    }

    public boolean isGuardado() {
        return guardado;
    }
}
