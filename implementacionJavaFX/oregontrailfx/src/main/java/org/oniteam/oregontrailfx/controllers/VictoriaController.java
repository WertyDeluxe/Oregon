package org.oniteam.oregontrailfx.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.oniteam.oregontrailfx.model.GameManager;

import java.net.URL;
import java.util.ResourceBundle;

public class VictoriaController implements  Initializable {
    @FXML private Label lblResultado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameManager gm = GameManager.getInstance();
        String resultado = "Días de viaje: " + gm.getDiaActual() + "\n";
        resultado += "Supervivientes: " + gm.getCaravana().getMiembrosVivos() + "\n";
        resultado += "Dinero restante: $" + String.format("%.0f", gm.getPlayer().getDinero()) + "\n";
        resultado += "Comida restante: " + gm.getCaravana().getComida() + " lbs";
        lblResultado.setText(resultado);
    }

    @FXML
    private void handleJugarDeNuevo() {
        cargarVista("/views/inicio.fxml", "Oregon Trail");
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
            e.printStackTrace();
        }
    }


}
