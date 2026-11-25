package pe.edu.upeu.taller_git_gp1.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pe.edu.upeu.taller_git_gp1.HelloApplication;
import pe.edu.upeu.taller_git_gp1.implementacion.DetalleVentaDaoImpl;
import pe.edu.upeu.taller_git_gp1.implementacion.VentaDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IDetalleVentaDao;
import pe.edu.upeu.taller_git_gp1.interfaces.IVentaDao;
import pe.edu.upeu.taller_git_gp1.modelo.DetalleVenta;
import pe.edu.upeu.taller_git_gp1.modelo.Venta;
import pe.edu.upeu.taller_git_gp1.utils.AlertsUtils;

import java.time.LocalDate;

public class VentaController {
    private static final AlertsUtils alertsUtils = new AlertsUtils();

    @FXML
    private TableView<Venta> tableVenta;
    @FXML
    private TableColumn<Venta, Number> ColNumero;
    @FXML
    private TableColumn<Venta, Integer> ColID;
    @FXML
    private TableColumn<Venta, String> ColCliente;
    @FXML
    private TableColumn<Venta, String> ColUsuario;
    @FXML
    private TableColumn<Venta, LocalDate> ColFecha;
    @FXML
    private TableColumn<Venta, Double> ColTotal;

    private final IVentaDao ventaDao = new VentaDaoImpl();
    private final IDetalleVentaDao detalleVentaDao = new DetalleVentaDaoImpl();
    private ObservableList<Venta> listaVenta;

    @FXML
    private void initialize() {
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

        ColID.setCellValueFactory(
                cd -> cd.getValue().idventaProperty().asObject());

        ColCliente.setCellValueFactory(
                cd -> cd.getValue().getCliente().nombresProperty());

        ColUsuario.setCellValueFactory(
                cd -> cd.getValue().getUsuario().userProperty());

        ColFecha.setCellValueFactory(
                cd -> cd.getValue().fechaventaProperty());

        ColTotal.setCellValueFactory(
                cd -> cd.getValue().totalProperty().asObject());

        cargarDatos();
    }
    private void cargarDatos() {
        listaVenta = FXCollections.observableArrayList(ventaDao.listAll());
        tableVenta.setItems(listaVenta);
    }
    @FXML
    private void handleNew() {
        showForm();
    }
    private void showForm() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(
                            "/pe/edu/upeu/taller_git_gp1/views/venta-form.fxml"
                    )
            );

            Parent root = loader.load();
            VentaFormController controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Nueva Venta");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (controller.isSaved()) {
                cargarDatos();
                Alert alert = alertsUtils.viewAlerts(
                        "Registro exitoso",
                        "Venta registrada correctamente.",
                        "CONFIRMATION"
                );
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleVerVenta() {
        Venta ventaSeleccionada =
                tableVenta.getSelectionModel().getSelectedItem();

        if (ventaSeleccionada == null) {
            Alert alert = alertsUtils.viewAlerts(
                    "Selección requerida",
                    "Seleccione una venta para ver su boleta",
                    "WARNING"
            );
            alert.showAndWait();
            return;
        }
        try {
            Venta venta = ventaDao.searchById(
                    ventaSeleccionada.getIdventa()
            );
            ObservableList<DetalleVenta> detalles =
                    FXCollections.observableArrayList(
                            detalleVentaDao.listByVenta(
                                    ventaSeleccionada.getIdventa()
                            )
                    );
            if (venta == null || detalles.isEmpty()) {
                Alert alert = alertsUtils.viewAlerts(
                        "Aviso",
                        "No se encontró la información de la venta",
                        "WARNING"
                );
                alert.showAndWait();
                return;
            }
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(
                            "/pe/edu/upeu/taller_git_gp1/views/boleta-view.fxml"
                    )
            );
            Parent root = loader.load();
            BoletaController controller = loader.getController();
            controller.recibirVenta(venta, detalles);

            Stage stage = new Stage();
            stage.setTitle("Boleta de venta");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

