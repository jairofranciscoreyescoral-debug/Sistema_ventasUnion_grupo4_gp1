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
        cbGenero.getItems().addAll("Masculino", "Femenino");
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
        if (txtDni.getText().length() != 8) {
            Alert alert = alertsUtils.viewAlerts(
                    "DNI incorrecto",
                    "El DNI debe tener exactamente 8 números.",
                    "WARNING"
            );
            alert.showAndWait();
            return false;
        }
        for (int i = 0; i < txtDni.getText().length(); i++) {
            if (txtDni.getText().charAt(i) < '0' ||
                    txtDni.getText().charAt(i) > '9') {
                Alert alert = alertsUtils.viewAlerts(
                        "DNI incorrecto",
                        "El DNI solo debe contener números.",
                        "WARNING"
                );
                alert.showAndWait();
                return false;
            }
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
