package pe.edu.upeu.taller_git_gp1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pe.edu.upeu.taller_git_gp1.implementacion.ClienteDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IClienteDao;
import pe.edu.upeu.taller_git_gp1.modelo.Cliente;
import pe.edu.upeu.taller_git_gp1.utils.AlertsUtils;

public class ClienteFormController {
    private static AlertsUtils alertsUtils = new AlertsUtils();
    private final IClienteDao clienteDao = new ClienteDaoImpl();
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtNombre, txtApellido, txtDni, txtEmail;
    @FXML
    private ComboBox<String> cbGenero;
    private Cliente cliente;
    private boolean saved = false;
    @FXML
    public void initialize() {
        cbGenero.getItems().addAll("M", "F");
    }
    private boolean validated() {
        if (txtNombre.getText().isEmpty() || txtDni.getText().isEmpty()) {
            Alert alert = alertsUtils.viewAlerts(
                    "ERROR",
                    "Nombre y DNI obligatorio",
                    "WARNING"
            );

            alert.showAndWait();
            return false;
        }
        return true;
    }
    public Cliente getCliente() {
        return cliente;
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
            Cliente existente = clienteDao.searchByDni(txtDni.getText());
            if (existente != null &&
                    (cliente == null ||
                            existente.getIdcliente() != cliente.getIdcliente())) {
                Alert alert = alertsUtils.viewAlerts(
                        "DNI duplicado",
                        "Este DNI ya está registrado. Ingrese otro DNI.",
                        "WARNING"
                );
                alert.showAndWait();
                return;
            }
            if (cliente == null) {
                cliente = new Cliente();
            }
            cliente.setNombres(txtNombre.getText());
            cliente.setApellidos(txtApellido.getText());
            cliente.setDni(txtDni.getText());
            cliente.setEmail(txtEmail.getText());
            cliente.setGenero(cbGenero.getValue());

            saved = true;
            cerrar();
        }
    }
    public void setCliente(Cliente c) {
        this.cliente = c;
        if (c != null) {
            lblTitulo.setText("Editar Cliente");

            txtNombre.setText(c.getNombres());
            txtApellido.setText(c.getApellidos());
            txtDni.setText(c.getDni());
            txtEmail.setText(c.getEmail());

            cbGenero.setValue(c.getGenero());
        }
    }
}
