package pe.edu.upeu.taller_git_gp1.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import pe.edu.upeu.taller_git_gp1.modelo.UserSession;

import java.io.IOException;

public class MainController {
    @FXML
    private StackPane contentArea;
    @FXML
    private Label lblUsuario;
    @FXML
    public void initialize(){
        if (UserSession.getInstance().getUserLogger() != null) {
            lblUsuario.setText("Bienvenido - "+UserSession.getInstance().
                    getUserLogger().getNombres());
        }
        showHome();
    }
    @FXML
    private void handleLogout(){
        UserSession.getInstance().cleanSession();
        System.exit(0);
    }
    @FXML
    private void showHome(){
        loadview("home-view.fxml");
    }
    @FXML
    private void showCliente(){
        loadview("cliente-view.fxml");
    }
    @FXML
    private void showProducto(){
        loadview("producto-view.fxml");
    }
    @FXML
    private void showVenta(){
        loadview("venta-view.fxml");
    }
    private void loadview(String fxml) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass()
                    .getResource("/pe/edu/upeu/taller_git_gp1/views/"+fxml));
            Parent view =loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Error de carga de la vista" + e.getMessage());
        }
    }
}
