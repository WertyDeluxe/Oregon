package org.oniteam.oregontrailfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("🚀 Iniciando aplicación...");

        // Intentar cargar el archivo
        String rutaFXML = "/org/oniteam/oregontrailfx/inicio.fxml";
        System.out.println("📂 Buscando archivo en: " + rutaFXML);

        var recurso = Main.class.getResource(rutaFXML);

        if (recurso == null) {
            System.err.println("❌ ERROR: No se encontró el archivo FXML en: " + rutaFXML);
            System.err.println("💡 Verifica que el archivo esté en: src/main/resources/org/oniteam/oregontrailfx/inicio.fxml");
            throw new IOException("Archivo FXML no encontrado: " + rutaFXML);
        }

        System.out.println("✅ Archivo encontrado en: " + recurso);

        FXMLLoader fxmlLoader = new FXMLLoader(recurso);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 700);

        stage.setTitle("Oregon Trail Survival");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        System.out.println("✅ Aplicación iniciada correctamente");
    }

    public static void main(String[] args) {
        launch();
    }
}