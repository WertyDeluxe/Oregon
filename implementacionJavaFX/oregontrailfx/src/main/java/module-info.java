module org.oniteam.oregontrailfx {
    // Módulos de JavaFX requeridos
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // Módulos de Java necesarios para Gemini API
    requires java.net.http;  // Para HttpClient (opcional, usas HttpURLConnection)

    // Abrir paquetes a JavaFX para reflexión (FXML)
    opens org.oniteam.oregontrailfx to javafx.fxml;
    opens org.oniteam.oregontrailfx.controllers to javafx.fxml;

    // Exportar paquetes principales
    exports org.oniteam.oregontrailfx;
    exports org.oniteam.oregontrailfx.controllers;
    exports org.oniteam.oregontrailfx.model;
    exports org.oniteam.oregontrailfx.structures;
    exports org.oniteam.oregontrailfx.util;

}