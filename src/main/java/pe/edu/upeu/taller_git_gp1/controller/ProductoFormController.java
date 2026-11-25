package pe.edu.upeu.taller_git_gp1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import pe.edu.upeu.taller_git_gp1.modelo.Producto;
import pe.edu.upeu.taller_git_gp1.utils.AlertsUtils;

import java.awt.*;

public class ProductoFormController {
    private static AlertsUtils alertsUtils = new AlertsUtils();
    @FXML
    private Label lblTitulo;

    @FXML
    private TextField txtNombre, txtPrecio, txtStock;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private ComboBox<String> cbEstado;

    private Producto producto;
    private boolean saved = false;
    @FXML
    public void initialize() {
        cbEstado.getItems().addAll("Disponible", "No Stock");
        cbTipo.getItems().addAll("Pan", "Bebida", "Bolleria", "Snack", "Cereal", "Untable", "Paneton");
        cbEstado.setDisable(true);
    }
    private boolean validated() {
        if (txtNombre.getText().isEmpty()
                || txtPrecio.getText().isEmpty()
                || txtStock.getText().isEmpty()
                || cbTipo.getValue() == null) {
            Alert alert = alertsUtils.viewAlerts(
                    "ERROR",
                    "Todos los campos son obligatorios",
                    "WARNING"
            );
            alert.show();
            return false;
        }
        try {
            Double.parseDouble(txtPrecio.getText());
            Integer.parseInt(txtStock.getText());
        } catch (NumberFormatException e) {
            Alert alert = alertsUtils.viewAlerts(
                    "ERROR",
                    "Precio y Stock deben ser valores numéricos",
                    "WARNING"
            );
            alert.show();
            return false;
        }
        return true;
    }

    public Producto getProducto() {
        return producto;
    }

    public boolean isSaved() {
        return saved;
    }

    private void cerrar() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        cerrar();
    }

    @FXML
    private void handleSave() {
        if (validated()) {
            if (producto == null)
                producto = new Producto();
            producto.setNombre(txtNombre.getText());
            producto.setTipoProducto(cbTipo.getValue());
            producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
            producto.setStock(Integer.parseInt(txtStock.getText()));

            producto.setEstado(
                    producto.getStock() == 0 ? "No Stock" : "Disponible"
            );

            cbEstado.setValue(producto.getEstado());

            saved = true;
            cerrar();
        }
    }
    public void setProducto(Producto p) {
        this.producto = p;
        if (p != null) {
            lblTitulo.setText("Editar producto");

            txtNombre.setText(p.getNombre());
            txtPrecio.setText(String.valueOf(p.getPrecio()));
            txtStock.setText(String.valueOf(p.getStock()));

            cbTipo.setValue(p.getTipoProducto());

            cbEstado.setValue(
                    p.getStock() == 0 ? "No Stock" : "Disponible"
            );
        }
    }
}
