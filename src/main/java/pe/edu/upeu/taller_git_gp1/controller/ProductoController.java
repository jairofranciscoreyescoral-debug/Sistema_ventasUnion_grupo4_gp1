package pe.edu.upeu.taller_git_gp1.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pe.edu.upeu.taller_git_gp1.HelloApplication;
import pe.edu.upeu.taller_git_gp1.implementacion.ProductoDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IProductoDao;
import pe.edu.upeu.taller_git_gp1.modelo.Producto;
import pe.edu.upeu.taller_git_gp1.utils.AlertsUtils;

import java.util.Optional;

public class ProductoController {
    private static final AlertsUtils alertsUtils = new AlertsUtils();

    @FXML
    private TableView<Producto> tableProducto;
    @FXML
    private TableColumn<Producto, Integer> ColID;
    @FXML
    private TableColumn<Producto, Number> ColNumber;
    @FXML
    private TableColumn<Producto, String> ColNombre;
    @FXML
    private TableColumn<Producto, String> ColTipo;
    @FXML
    private TableColumn<Producto, Double> ColPrecio;
    @FXML
    private TableColumn<Producto, Integer> ColStock;
    @FXML
    private TableColumn<Producto, String> ColEstado;
    @FXML
    private TextField txtBuscar;

    private final IProductoDao productoDao = new ProductoDaoImpl();
    private ObservableList<Producto> listaProductos;

    @FXML
    public void initialize() {
        ColNumber.setCellFactory(column -> new TableCell<>() {
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
                data -> data.getValue().idproductoProperty().asObject());

        ColNombre.setCellValueFactory(
                data -> data.getValue().nombreProperty());

        ColTipo.setCellValueFactory(
                data -> data.getValue().tipoProductoProperty());

        ColPrecio.setCellValueFactory(
                data -> data.getValue().precioProperty().asObject());

        ColStock.setCellValueFactory(
                data -> data.getValue().stockProperty().asObject());

        ColEstado.setCellValueFactory(
                data -> data.getValue().estadoProperty());

        cargarDatos();
    }

    private void cargarDatos() {
        listaProductos = FXCollections.observableArrayList(
                productoDao.listAll()
        );
        tableProducto.setItems(listaProductos);
    }

    @FXML
    private void buscarProducto() {
        String buscar = txtBuscar.getText().toLowerCase();

        ObservableList<Producto> productosFiltrados =
                FXCollections.observableArrayList();

        for (Producto producto : listaProductos) {
            if (producto.getNombre().toLowerCase().contains(buscar)) {
                productosFiltrados.add(producto);
            }
        }
        tableProducto.setItems(productosFiltrados);
    }

    @FXML
    public void handleNew() {
        showForm(null);
    }

    @FXML
    private void handleEdit() {
        Producto selectProducto =
                tableProducto.getSelectionModel().getSelectedItem();

        if (selectProducto != null) {
            Producto productoFull =
                    productoDao.searchById(selectProducto.getIdproducto());
            showForm(productoFull);
        } else {
            Alert alert = alertsUtils.viewAlerts(
                    "Seleccion requerida",
                    "Seleccione un producto para editar",
                    "WARNING"
            );
            alert.show();
        }
    }

    @FXML
    private void handleDelete() {
        Producto selectProducto =
                tableProducto.getSelectionModel().getSelectedItem();

        if (selectProducto != null) {
            Alert confirm = alertsUtils.viewAlerts(
                    "Confirmar eliminación",
                    "¿Estás seguro de eliminar el producto? - "
                            + selectProducto.getNombre(),
                    "CONFIRMATION"
            );
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (productoDao.delete(selectProducto)) {
                    cargarDatos();
                    buscarProducto();
                } else {
                    Alert confirmWarning = alertsUtils.viewAlerts(
                            "Error",
                            "No se pudo eliminar el producto",
                            "WARNING"
                    );
                    confirmWarning.showAndWait();
                }
            }
        } else {
            Alert alert = alertsUtils.viewAlerts(
                    "Seleccion requerida",
                    "Seleccione un producto para eliminar",
                    "WARNING"
            );
            alert.show();
        }
    }
    private void showForm(Producto p) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(
                            "/pe/edu/upeu/taller_git_gp1/views/producto-form.fxml"
                    )
            );
            Parent root = loader.load();
            ProductoFormController controller = loader.getController();
            controller.setProducto(p);

            Stage stage = new Stage();
            stage.setTitle(p == null ? "Nuevo Producto" : "Editar Producto");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            if (controller.isSaved()) {
                Producto productoProcesado = controller.getProducto();
                if (p == null) {
                    boolean result = productoDao.insert(productoProcesado);
                    if (result) {
                        Alert alert = alertsUtils.viewAlerts(
                                "Success",
                                "Registro satisfactorio!!!",
                                "CONFIRMATION"
                        );
                        alert.show();
                    } else {
                        Alert alert = alertsUtils.viewAlerts(
                                "Error",
                                "Error al insertar!!!",
                                "ERROR"
                        );
                        alert.show();
                    }
                } else {
                    boolean result = productoDao.update(productoProcesado);

                    if (result) {
                        Alert alert = alertsUtils.viewAlerts(
                                "Update",
                                "Registro actualizado!!!",
                                "WARNING"
                        );
                        alert.showAndWait();
                    } else {
                        Alert alert = alertsUtils.viewAlerts(
                                "Error",
                                "Error al actualizar!!!",
                                "ERROR"
                        );
                        alert.show();
                    }
                }
                cargarDatos();
                buscarProducto();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
