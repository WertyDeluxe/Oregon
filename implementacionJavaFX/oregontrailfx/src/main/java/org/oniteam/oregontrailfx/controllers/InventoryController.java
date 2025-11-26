package org.oniteam.oregontrailfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.oniteam.oregontrailfx.model.*;
import org.oniteam.oregontrailfx.structures.ListaEnlazada;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * Controlador para gestionar el inventario del jugador.
 * RF8: Inventario con prioridad a esenciales.
 */
public class InventoryController implements Initializable {

    @FXML private TableView<ItemDisplay> tableInventario;
    @FXML private TableColumn<ItemDisplay, String> colNombre;
    @FXML private TableColumn<ItemDisplay, String> colTipo;
    @FXML private TableColumn<ItemDisplay, Integer> colCantidad;
    @FXML private TableColumn<ItemDisplay, String> colDescripcion;

    @FXML private Label lblCapacidad;
    @FXML private ComboBox<String> cmbOrdenar;
    @FXML private TextField txtBuscar;
    @FXML private Label lblEstadoJugador;

    private Inventory inventory;
    private Player player;
    private GameManager gameManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameManager = GameManager.getInstance();
        player = gameManager.getPlayer();

        // Crear inventario de prueba si no existe
        inventory = new Inventory(20);
        agregarItemsIniciales();

        configurarTabla();
        configurarComboOrdenar();
        actualizarVista();
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    }

    private void configurarComboOrdenar() {
        cmbOrdenar.setItems(FXCollections.observableArrayList(
                "Sin ordenar",
                "Por tipo (A-Z)",
                "Por cantidad (menor a mayor)",
                "Por prioridad (medicinas > comida > munición)"
        ));
        cmbOrdenar.setValue("Sin ordenar");
        cmbOrdenar.setOnAction(e -> handleOrdenar());
    }

    private void agregarItemsIniciales() {
        // Agregar algunos items de prueba
        inventory.add(ItemInventory.food(10));
        inventory.add(ItemInventory.medkit(3));
        inventory.add(ItemInventory.ammo("Rifle", 50));
        inventory.add(ItemInventory.ammo("Revolver", 30));
    }

    private void actualizarVista() {
        ObservableList<ItemDisplay> items = FXCollections.observableArrayList();

        ListaEnlazada<ItemInventory> listaItems = inventory.getItems();
        Iterator<ItemInventory> it = listaItems.iterator();

        while (it.hasNext()) {
            ItemInventory item = it.next();
            items.add(new ItemDisplay(
                    item.getNombre(),
                    item.getTipo().toString(),
                    item.getCantidad(),
                    item.getDescripcion()
            ));
        }

        tableInventario.setItems(items);

        lblCapacidad.setText(String.format(
                "Capacidad: %d/20 items",
                inventory.size()
        ));

        if (player != null) {
            lblEstadoJugador.setText(String.format(
                    "Vida: %d | Dinero: $%.0f",
                    player.getVida(),
                    player.getDinero()
            ));
        }
    }

    @FXML
    private void handleOrdenar() {
        String opcion = cmbOrdenar.getValue();

        switch (opcion) {
            case "Por tipo (A-Z)" -> inventory.sortByTypeBubble();
            case "Por cantidad (menor a mayor)" -> inventory.sortByQuantitySelection();
            case "Por prioridad (medicinas > comida > munición)" -> inventory.sortByPriorityInsertion();
        }

        actualizarVista();
    }

    @FXML
    private void handleBuscar() {
        String nombre = txtBuscar.getText().trim();

        if (nombre.isEmpty()) {
            mostrarAlerta("Ingresa un nombre para buscar", Alert.AlertType.WARNING);
            return;
        }

        ItemInventory encontrado = inventory.linearSearchByName(nombre);

        if (encontrado != null) {
            mostrarAlerta(
                    "Item encontrado:\n" + encontrado.toString(),
                    Alert.AlertType.INFORMATION
            );
        } else {
            mostrarAlerta(
                    "No se encontró un item con el nombre: " + nombre,
                    Alert.AlertType.WARNING
            );
        }
    }

    @FXML
    private void handleUsar() {
        ItemDisplay selected = tableInventario.getSelectionModel().getSelectedItem();

        if (selected == null) {
            mostrarAlerta("Selecciona un item para usar", Alert.AlertType.WARNING);
            return;
        }

        // Buscar el item real en el inventario
        ItemInventory item = inventory.linearSearchByName(selected.getNombre());

        if (item != null && player != null) {
            boolean usado = inventory.use(item, player);

            if (usado) {
                mostrarAlerta(
                        "Has usado: " + item.getNombre(),
                        Alert.AlertType.INFORMATION
                );
                actualizarVista();
            } else {
                mostrarAlerta(
                        "No se pudo usar el item",
                        Alert.AlertType.WARNING
                );
            }
        }
    }

    @FXML
    private void handleDescartar() {
        ItemDisplay selected = tableInventario.getSelectionModel().getSelectedItem();

        if (selected == null) {
            mostrarAlerta("Selecciona un item para descartar", Alert.AlertType.WARNING);
            return;
        }

        ItemInventory item = inventory.linearSearchByName(selected.getNombre());

        if (item != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar");
            confirm.setHeaderText("¿Descartar este item?");
            confirm.setContentText(item.toString());

            if (confirm.showAndWait().get() == ButtonType.OK) {
                inventory.remove(item);
                actualizarVista();
            }
        }
    }

    @FXML
    private void handleCerrar() {
        Stage stage = (Stage) tableInventario.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Clase auxiliar para mostrar items en la TableView.
     */
    public static class ItemDisplay {
        private String nombre;
        private String tipo;
        private int cantidad;
        private String descripcion;

        public ItemDisplay(String nombre, String tipo, int cantidad, String descripcion) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.cantidad = cantidad;
            this.descripcion = descripcion;
        }

        public String getNombre() { return nombre; }
        public String getTipo() { return tipo; }
        public int getCantidad() { return cantidad; }
        public String getDescripcion() { return descripcion; }
    }
}