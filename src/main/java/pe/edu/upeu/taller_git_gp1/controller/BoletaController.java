package pe.edu.upeu.taller_git_gp1.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import pe.edu.upeu.taller_git_gp1.modelo.Venta;
import pe.edu.upeu.taller_git_gp1.modelo.DetalleVenta;

public class BoletaController {
    @FXML
    private Label lblNumero;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblCliente;
    @FXML
    private Label lblDNI;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblTotal;
    @FXML
    private TableView<DetalleVenta> tableBoleta;
    @FXML
    private TableColumn<DetalleVenta, String> ColProducto;
    @FXML
    private TableColumn<DetalleVenta, Integer> ColCantidad;
    @FXML
    private TableColumn<DetalleVenta, Double> ColPrecio;
    @FXML
    private TableColumn<DetalleVenta, Double> ColSubtotal;

    private ObservableList<DetalleVenta> listaDetalle =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        ColProducto.setCellValueFactory(cd ->
                cd.getValue().getProducto().nombreProperty()
        );
        ColCantidad.setCellValueFactory(cd ->
                cd.getValue().cantidadProperty().asObject()
        );
        ColPrecio.setCellValueFactory(cd ->
                cd.getValue().precioProperty().asObject()
        );
        ColSubtotal.setCellValueFactory(cd ->
                cd.getValue().subtotalProperty().asObject()
        );
        tableBoleta.setItems(listaDetalle);
    }

    public void recibirVenta(Venta venta,
                             ObservableList<DetalleVenta> detalles) {
        lblNumero.setText(String.format("%06d", venta.getIdventa()));
        lblFecha.setText(venta.getFechaventa().toString());
        lblCliente.setText(
                venta.getCliente().getNombres() + " " +
                        venta.getCliente().getApellidos()
        );
        lblDNI.setText(venta.getCliente().getDni());
        lblUsuario.setText(
                venta.getUsuario().getNombres() + " " +
                        venta.getUsuario().getApellidos()
        );
        lblTotal.setText(
                String.format("S/. %.2f", venta.getTotal())
        );
        listaDetalle.setAll(detalles);
    }
}
