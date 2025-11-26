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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/oniteam/oregontrailfx/achievements.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Árbol de Logros");
            stage.setScene(new Scene(root, 900, 700));
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            System.err.println(" Error abriendo logros desde menú");
            e.printStackTrace();
        }
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