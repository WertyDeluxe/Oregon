package org.oniteam.oregontrailfx.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.oniteam.oregontrailfx.model.GameManager;
import org.oniteam.oregontrailfx.model.Miembro;
import org.oniteam.oregontrailfx.model.Player;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfiguracionController implements Initializable {
    @FXML private TextField txtNombre, txtMiembro1, txtMiembro2, txtMiembro3, txtMiembro4;
    @FXML private ComboBox<String> cmbProfesion;
    @FXML private Label lblDineroInicial;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbProfesion.setItems(FXCollections.observableArrayList("Banquero", "Carpintero", "Granjero"));
        cmbProfesion.setValue("Carpintero");
        actualizarDinero();

        cmbProfesion.setOnAction(e -> actualizarDinero());
    }

    private void actualizarDinero() {
        String prof = cmbProfesion.getValue();
        int dinero = prof.equals("Banquero") ? 1600 : prof.equals("Carpintero") ? 800 : 400;
        lblDineroInicial.setText("Dinero inicial: $" + dinero);
    }

    @FXML
    private void handleContinuar() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("Debes ingresar tu nombre");
            return;
        }

        GameManager gm = GameManager.getInstance();
        gm.iniciarJuego(txtNombre.getText().trim(), cmbProfesion.getValue());

        // ✅ IMPORTANTE: Establecer posición inicial del jugador
        Player player = gm.getPlayer();
        if (player != null) {
            player.setPosition(5, 5);
            System.out.println("✅ Jugador configurado - Nombre: " + player.getNombre() + " | Posición: (5, 5)");
        }

        agregarMiembro(txtMiembro1.getText().trim(), 35);
        agregarMiembro(txtMiembro2.getText().trim(), 10);
        agregarMiembro(txtMiembro3.getText().trim(), 8);
        agregarMiembro(txtMiembro4.getText().trim(), 6);

        cargarVista("/org/oniteam/oregontrailfx/game-view.fxml", "Oregon Trail - Juego");
    }

    private void agregarMiembro(String nombre, int edad) {
        if (!nombre.isEmpty()) {
            GameManager.getInstance().getCaravana().agregarMiembro(new Miembro(nombre, edad));
        }
    }

    @FXML
    private void handleVolver() {
        cargarVista("/org/oniteam/oregontrailfx/inicio.fxml", "Oregon Trail");
    }

    private void cargarVista(String fxml, String titulo) {
        try {
            System.out.println("Intentando cargar: " + fxml);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            Scene scene = new Scene(root, 900, 700);
            stage.setTitle(titulo);
            stage.setScene(scene);

            System.out.println("Vista cargada exitosamente: " + fxml);
        } catch (Exception e) {
            System.err.println("❌ Error cargando vista: " + fxml);
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}