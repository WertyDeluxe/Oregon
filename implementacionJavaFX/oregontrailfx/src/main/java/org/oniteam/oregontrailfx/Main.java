package org.oniteam.oregontrailfx;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // ⚠️ CAMBIO: Cargar inicio.fxml en lugar de game-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/oniteam/oregontrailfx/inicio.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 900, 700);
            primaryStage.setTitle("The Oregon Trail");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
