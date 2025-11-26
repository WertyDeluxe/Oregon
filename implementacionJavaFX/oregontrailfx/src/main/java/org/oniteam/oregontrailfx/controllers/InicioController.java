package org.oniteam.oregontrailfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class InicioController {

    @FXML
    private void handleComenzar(ActionEvent event) {
        cargarVista("/org/oniteam/oregontrailfx/configuracion.fxml", "Configuración", event);
    }

    @FXML
    private void handleInstrucciones() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instrucciones");
        alert.setHeaderText("Cómo Jugar Oregon Trail");
        alert.setContentText("1. Elige tu profesión y nombra a tu familia\n" +
                "2. Compra suministros en la tienda\n" +
                "3. Viaja 2000 millas hasta Oregón\n" +
                "4. Gestiona tus recursos\n" +
                "5. Caza para conseguir comida\n" +
                "6. Enfrenta eventos aleatorios\n\n" +
                "¡Buena suerte en tu viaje!");
        alert.showAndWait();
    }

    @FXML
    private void handleSalir() {
        System.exit(0);
    }

    private void cargarVista(String fxml, String titulo, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            // Obtener el Stage actual del botón que disparó el evento
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 900, 700);
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            System.err.println("Error al cargar la vista: " + fxml);
            e.printStackTrace();
        }
    }
}