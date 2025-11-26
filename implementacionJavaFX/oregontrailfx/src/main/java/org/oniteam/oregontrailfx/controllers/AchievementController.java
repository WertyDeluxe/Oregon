package org.oniteam.oregontrailfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.oniteam.oregontrailfx.model.OregonTrail;
import org.oniteam.oregontrailfx.model.TreeAchivement;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para mostrar el árbol de logros.
 * RF17: El sistema permite visualizar el árbol de logros en ventana independiente.
 */
public class AchievementController implements Initializable {

    @FXML private VBox rootVBox;
    @FXML private Label lblTitulo;
    @FXML private TextArea txtLogrosDesbloqueados;
    @FXML private TextArea txtTodosLosLogros;
    @FXML private Label lblEstadisticas;

    private OregonTrail oregonTrail;
    private TreeAchivement treeAchivement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarLogros();
        mostrarLogros();
    }

    /**
     * Carga el árbol de logros desde OregonTrail.
     */
    private void cargarLogros() {
        oregonTrail = new OregonTrail();
        oregonTrail.insertAllachivementsToArraylist();
        oregonTrail.insertAchivementsToTree();
        treeAchivement = oregonTrail.getTreeAchivement();
    }

    /**
     * Muestra los logros en los TextArea usando recorrido InOrder.
     */
    private void mostrarLogros() {
        // Logros desbloqueados por el jugador
        String logrosPlayer = treeAchivement.inOrder();
        txtLogrosDesbloqueados.setText(
                logrosPlayer.isEmpty() || logrosPlayer.contains("vacío")
                        ? "Aún no has desbloqueado ningún logro.\n¡Explora el sendero para conseguirlos!"
                        : formatearLogros(logrosPlayer)
        );

        // Todos los logros disponibles
        String todosLogros = treeAchivement.inordenRootAllAchivements();
        txtTodosLosLogros.setText(formatearLogros(todosLogros));

        // Estadísticas
        int desbloqueados = treeAchivement.countPlayerAchivements();
        int totales = treeAchivement.countAllAchivements();
        double porcentaje = totales > 0 ? (desbloqueados * 100.0 / totales) : 0;

        lblEstadisticas.setText(String.format(
                "Progreso: %d/%d logros (%.1f%%)",
                desbloqueados, totales, porcentaje
        ));
    }

    /**
     * Formatea la salida del recorrido InOrder para mejor legibilidad.
     */
    private String formatearLogros(String logrosRaw) {
        if (logrosRaw == null || logrosRaw.trim().isEmpty()) {
            return "No hay logros disponibles";
        }

        // Separar por " | " que es el formato del toString() de Achivement
        String[] logros = logrosRaw.split("\\|");
        StringBuilder sb = new StringBuilder();

        for (String logro : logros) {
            if (!logro.trim().isEmpty()) {
                sb.append(logro.trim()).append("\n\n");
            }
        }

        return sb.toString();
    }

    /**
     * Simula desbloquear un logro (para testing).
     * En el juego real, esto se llamaría cuando se cumple una condición.
     */
    @FXML
    private void handleSimularLogro() {
        // Desbloquear el logro de dificultad 1 (Primer Paso)
        oregonTrail.unlockAchivement(1);
        mostrarLogros();
    }

    /**
     * Cierra la ventana del árbol de logros.
     */
    @FXML
    private void handleCerrar() {
        Stage stage = (Stage) rootVBox.getScene().getWindow();
        stage.close();
    }

    /**
     * Actualiza la visualización del árbol.
     */
    @FXML
    private void handleActualizar() {
        mostrarLogros();
    }

    /**
     * Exporta los logros a un String (útil para guardado).
     */
    public String exportarLogros() {
        return treeAchivement.inOrder();
    }
}