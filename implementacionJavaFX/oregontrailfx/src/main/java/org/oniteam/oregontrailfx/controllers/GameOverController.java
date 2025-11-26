package org.oniteam.oregontrailfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.oniteam.oregontrailfx.model.GameManager;

import java.net.URL;
import java.util.ResourceBundle;

public class GameOverController implements Initializable {
    @FXML private TextArea lblResultado;
    @FXML private Label lblCausa;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameManager gm = GameManager.getInstance();

        String resultado = "=== REPORTE FINAL ===\n\n";
        resultado += "Días de viaje: " + gm.getDiaActual() + "\n";
        resultado += "Distancia recorrida: " + gm.getDistanciaRecorrida() + " millas\n";
        resultado += "Supervivientes: " + gm.getCaravana().getMiembrosVivos() + "\n";
        resultado += "Dinero restante: $" + String.format("%.0f", gm.getPlayer().getDinero()) + "\n";
        resultado += "Comida restante: " + gm.getCaravana().getComida() + " lbs\n";

        lblResultado.setText(resultado);

        // Determinar causa de la derrota
        if (gm.getCaravana().todosMuertos()) {
            lblCausa.setText("💀 Causa: Todos los miembros de la caravana han fallecido");
        } else if (gm.getPlayer().getVida() <= 0) {
            lblCausa.setText("💀 Causa: El líder de la caravana ha muerto");
        } else {
            lblCausa.setText("💀 Causa: El viaje no pudo completarse");
        }
    }

    @FXML
    private void handleJugarDeNuevo() {
        GameManager.getInstance().reiniciarJuego();
        cargarVista("/org/oniteam/oregontrailfx/inicio.fxml", "Oregon Trail");
    }

    @FXML
    private void handleSalir() {
        System.exit(0);
    }

    private void cargarVista(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = (Stage) lblResultado.getScene().getWindow();
            Scene scene = new Scene(root, 900, 700);
            stage.setTitle(titulo);
            stage.setScene(scene);
        } catch (Exception e) {
            System.err.println("Error cargando vista: " + fxml);
            e.printStackTrace();
        }
    }
}