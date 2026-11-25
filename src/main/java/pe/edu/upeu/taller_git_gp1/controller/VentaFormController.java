package pe.edu.upeu.taller_git_gp1.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import pe.edu.upeu.taller_git_gp1.implementacion.ClienteDaoImpl;
import pe.edu.upeu.taller_git_gp1.implementacion.DetalleVentaDaoImpl;
import pe.edu.upeu.taller_git_gp1.implementacion.VentaDaoImpl;

import pe.edu.upeu.taller_git_gp1.interfaces.IClienteDao;
import pe.edu.upeu.taller_git_gp1.interfaces.IDetalleVentaDao;
import pe.edu.upeu.taller_git_gp1.interfaces.IVentaDao;

import pe.edu.upeu.taller_git_gp1.modelo.*;
import pe.edu.upeu.taller_git_gp1.utils.AlertsUtils;

import java.io.IOException;
import java.time.LocalDate;

public class VentaFormController {
    private static final AlertsUtils alertUtils = new AlertsUtils();

    @FXML
    private Label lblTitulo, lblTotal, lblUsuario;

    @FXML
    private ComboBox<Cliente> cbCliente;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TableView<DetalleVenta> tableDetalle;

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

    private boolean guardado = false;

    private final IClienteDao clienteDao = new ClienteDaoImpl();
    private final IVentaDao ventaDao = new VentaDaoImpl();
    private final IDetalleVentaDao detalleVentaDao = new DetalleVentaDaoImpl();

    @FXML
    private void initialize() {

        cbCliente.setItems(
                FXCollections.observableArrayList(clienteDao.listAll())
        );

        cbCliente.setConverter(new StringConverter<Cliente>() {
            @Override
            public String toString(Cliente cliente) {
                if (cliente == null) {
                    return "";
                }
                return cliente.getNombres() + " " + cliente.getApellidos();
            }

            @Override
            public Cliente fromString(String string) {
                return null;
            }
        });

        // llenar las columnas :v
        ColProducto.setCellValueFactory(
                cd -> cd.getValue().getProducto().nombreProperty());

        ColCantidad.setCellValueFactory(
                cd -> cd.getValue().cantidadProperty().asObject());

        ColPrecio.setCellValueFactory(
                cd -> cd.getValue().precioProperty().asObject());

        ColSubtotal.setCellValueFactory(
                cd -> cd.getValue().subtotalProperty().asObject());

        tableDetalle.setItems(listaDetalle);

        Usuario usuario = UserSession.getInstance().getUserLogger();

        if (usuario != null) {
            lblUsuario.setText(usuario.getNombres());
        }

        dpFecha.setValue(LocalDate.now());

        actualizarTotal();
    }

    @FXML
    private void handleAddDetalle() {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/pe/edu/upeu/taller_git_gp1/views/detalleventa-form.fxml"
                    )
            );

            Parent root = loader.load();

            DetalleVentaFormController controller = loader.getController();

            controller.setVentaFormController(this);

            Stage stage = new Stage();
            stage.setTitle("Agregar productos");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recibirDetalles(ObservableList<DetalleVenta> detalles) {

        listaDetalle.setAll(detalles);

        tableDetalle.setItems(listaDetalle);

        actualizarTotal();
    }

    private void actualizarTotal() {

        double total = 0;

        for (DetalleVenta detalle : listaDetalle) {
            total += detalle.getSubtotal();
        }

        lblTotal.setText(String.format("%.2f", total));
    }

    @FXML
    private void handleSave() {

        if (cbCliente.getValue() == null || dpFecha.getValue() == null
                || listaDetalle.isEmpty()
                || UserSession.getInstance().getUserLogger() == null
        ) {
            Alert alert = alertUtils.viewAlerts(
                    "Error",
                    "Completar los campos y agregar productos",
                    "WARNING"
            );
            alert.showAndWait();
            return;
        }

        Venta v = new Venta();

        v.setCliente(cbCliente.getValue());
        v.setFechaventa(dpFecha.getValue());
        v.setUsuario(UserSession.getInstance().getUserLogger());

        double total = 0;

        for (DetalleVenta detalle : listaDetalle) {
            total += detalle.getSubtotal();
        }

        v.setTotal(total);

        if (ventaDao.insert(v)) {

            boolean correcto = true;

            for (DetalleVenta detalle : listaDetalle) {

                detalle.setVenta(v);

                if (!detalleVentaDao.insert(detalle)) {
                    correcto = false;
                }
            }

            if (correcto) {
                guardado = true;
                cerrar();
            } else {
                Alert alert = alertUtils.viewAlerts(
                        "Error",
                        "No se pudieron guardar todos los detalles",
                        "WARNING"
                );
                alert.showAndWait();
            }

        } else {
            Alert alert = alertUtils.viewAlerts(
                    "Error",
                    "No se pudo registrar la venta",
                    "WARNING"
            );
            alert.showAndWait();
        }
    }

    private void cerrar() {
        ((Stage) cbCliente.getScene().getWindow()).close();
    }

    @FXML
    private void handleCancel() {
        cerrar();
    }

    public boolean isGuardado() {
        return guardado;
    }
}
