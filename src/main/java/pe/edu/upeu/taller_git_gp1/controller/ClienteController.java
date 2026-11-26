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
import pe.edu.upeu.taller_git_gp1.HelloApplication;
import pe.edu.upeu.taller_git_gp1.implementacion.ClienteDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IClienteDao;
import pe.edu.upeu.taller_git_gp1.modelo.Cliente;
import pe.edu.upeu.taller_git_gp1.utils.AlertsUtils;

import java.util.Optional;

public class ClienteController {
    private static AlertsUtils alertsUtils = new AlertsUtils();

    @FXML
    private TableView<Cliente> tableCliente;
    @FXML
    private TableColumn<Cliente, Integer> ColID;
    @FXML
    private TableColumn<Cliente, Number> ColNumero;
    @FXML
    private TableColumn<Cliente, String> ColNombres;
    @FXML
    private TableColumn<Cliente, String> ColApellidos;
    @FXML
    private TableColumn<Cliente, String> ColDni;
    @FXML
    private TableColumn<Cliente, String> ColEmail;
    @FXML
    private TableColumn<Cliente, String> ColGenero;
    @FXML
    private TextField txtBuscar;

    private final IClienteDao clienteDao = new ClienteDaoImpl();
    private ObservableList<Cliente> listaCliente;

    @FXML
    public void initialize() {
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
                cellData -> cellData.getValue().idclienteProperty().asObject());

        ColNombres.setCellValueFactory(
                cellData -> cellData.getValue().nombresProperty());

        ColApellidos.setCellValueFactory(
                cellData -> cellData.getValue().apellidosProperty());

        ColDni.setCellValueFactory(
                cellData -> cellData.getValue().dniProperty());

        ColEmail.setCellValueFactory(
                cellData -> cellData.getValue().emailProperty());

        ColGenero.setCellValueFactory(
                cellData -> cellData.getValue().generoProperty());

        cargarDatos();
    }

    private void cargarDatos() {
        listaCliente = FXCollections.observableArrayList(clienteDao.listAll());
        tableCliente.setItems(listaCliente);
    }

    @FXML
    private void buscarCliente() {
        String dni = txtBuscar.getText();
        ObservableList<Cliente> clientesFiltrados =
                FXCollections.observableArrayList();
        for (Cliente cliente : listaCliente) {
            if (cliente.getDni().equals(dni)) {
                clientesFiltrados.add(cliente);
            }
        }
        tableCliente.setItems(clientesFiltrados);
    }

    @FXML
    private void handleDelete() {
        Cliente selectID = tableCliente.getSelectionModel().getSelectedItem();

        if (selectID != null) {
            Alert confirm = alertsUtils.viewAlerts(
                    "Confirmar eliminación",
                    "¿Estás seguro de eliminar Cliente? - " + selectID.getNombres(),
                    "CONFIRMATION"
            );

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (clienteDao.delete(selectID)) {
                    cargarDatos();
                    buscarCliente();
                } else {
                    Alert warning = alertsUtils.viewAlerts(
                            "Error",
                            "No se pudo eliminar el cliente.",
                            "WARNING"
                    );
                    warning.showAndWait();
                }
            }
        }
    }

    @FXML
    private void handleNew() {
        showForm(null);
    }
    @FXML
    private void handleEdit() {
        Cliente selectClient = tableCliente.getSelectionModel().getSelectedItem();
        if (selectClient != null) {
            Cliente clientFull = clienteDao.searchById(selectClient.getIdcliente());
            showForm(clientFull);
        } else {
            Alert alert = alertsUtils.viewAlerts(
                    "Selección requerida",
                    "Seleccione un cliente para editar.",
                    "WARNING"
            );
            alert.showAndWait();
        }
    }
    private void showForm(Cliente c) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(
                            "/pe/edu/upeu/taller_git_gp1/views/cliente-form.fxml"
                    )
            );
            Parent root = loader.load();
            ClienteFormController controller = loader.getController();
            controller.setCliente(c);

            Stage stage = new Stage();
            stage.setTitle(c == null ? "Nuevo Cliente" : "Editar Cliente");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            if (controller.isSaved()) {
                Cliente clienteProcesado = controller.getCliente();
                if (c == null) {
                    boolean result = clienteDao.insert(clienteProcesado);
                    if (result) {
                        Alert alert = alertsUtils.viewAlerts(
                                "Registro exitoso",
                                "Cliente registrado correctamente.",
                                "CONFIRMATION"
                        );
                        alert.showAndWait();
                    } else {
                        Alert alert = alertsUtils.viewAlerts(
                                "Error",
                                "No se pudo registrar el cliente.",
                                "ERROR"
                        );
                        alert.showAndWait();
                    }
                } else {
                    boolean result = clienteDao.update(clienteProcesado);
                    if (result) {
                        Alert alert = alertsUtils.viewAlerts(
                                "Actualización",
                                "Cliente actualizado correctamente.",
                                "CONFIRMATION"
                        );
                        alert.showAndWait();
                    } else {
                        Alert alert = alertsUtils.viewAlerts(
                                "Error",
                                "No se pudo actualizar el cliente.",
                                "ERROR"
                        );
                        alert.showAndWait();
                    }
                }
                cargarDatos();
                buscarCliente();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
