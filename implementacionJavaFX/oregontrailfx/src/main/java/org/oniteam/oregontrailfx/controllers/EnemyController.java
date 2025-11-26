package org.oniteam.oregontrailfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.oniteam.oregontrailfx.model.*;
import org.oniteam.oregontrailfx.model.GeminiService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para gestionar enemigos y diálogos con Gemini API.
 * RF18: Sistema de diálogo activable con NPC/enemigos/bots.
 * RF12: Comportamiento de enemigos.
 */
public class EnemyController implements Initializable {

    @FXML private Label lblEnemyInfo;
    @FXML private TextArea txtDialogo;
    @FXML private Label lblEstado;

    private Enemy currentEnemy;
    private ListEnemy enemies;
    private GeminiService geminiService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        geminiService = GeminiService.getInstance();
        enemies = new ListEnemy();
        crearEnemigosEjemplo();
        mostrarEnemigoActual();

        // Verificar configuración de Gemini
        if (!geminiService.isConfigured()) {
            txtDialogo.setText("Error en la API\n\n" +
                    geminiService.getMensajeConfiguracion());
        }
    }

    private void crearEnemigosEjemplo() {
        Enemy e1 = new Enemy(5, 5);
        Enemy e2 = new Enemy(10, 10);
        Enemy e3 = new Enemy(15, 15);

        enemies.addEnemy(e1);
        enemies.addEnemy(e2);
        enemies.addEnemy(e3);

        currentEnemy = e1;
    }

    private void mostrarEnemigoActual() {
        if (currentEnemy != null) {
            lblEnemyInfo.setText(String.format(
                    "Enemigo en posición (%d, %d) - Estado: %s",
                    currentEnemy.getX(),
                    currentEnemy.getY(),
                    currentEnemy.getState()
            ));
        }
    }

    /**
     * Genera un diálogo usando Gemini
     */
    @FXML
    private void handleEliminarEnemigo() {
        if (currentEnemy == null) {
            txtDialogo.setText("No hay enemigos activos");
            return;
        }

        lblEstado.setText("Generando diálogo con Gemini...");

        new Thread(() -> {
            String dialogo = geminiService.dialogoEnemigoDerrotado("bandido armado");

            javafx.application.Platform.runLater(() -> {
                txtDialogo.setText(
                        "ENEMIGO ELIMINADO\n" +
                                "--------------------------\n" +
                                "Posición: (" + currentEnemy.getX() + ", " + currentEnemy.getY() + ")\n\n" +
                                "Últimas palabras:\n" +
                                "\"" + dialogo + "\""
                );

                enemies.delete(currentEnemy);
                NodeEnemy next = enemies.getFirst();
                currentEnemy = (next != null) ? next.getData() : null;

                mostrarEnemigoActual();
                lblEstado.setText("Enemigo eliminado - " + enemies.contEnemies() + " restantes");
            });
        }).start();
    }

    /**
     * Genera un diálogo amigable con un NPC usando Gemini API.
     */
    @FXML
    private void handleDialogoAmigable() {
        lblEstado.setText("Generando diálogo con Gemini...");

        new Thread(() -> {
            String dialogo = geminiService.dialogoNPCAmigable();

            javafx.application.Platform.runLater(() -> {
                txtDialogo.setText(
                        "VIAJERO ENCONTRADO\n" +
                                "--------------------\n\n" + dialogo
                );
                lblEstado.setText("Diálogo generado");
            });
        }).start();
    }

    /**
     * Genera diálogo de comerciante.
     */
    @FXML
    private void handleDialogoComerciante() {
        lblEstado.setText("Generando diálogo con Gemini...");

        new Thread(() -> {
            String dialogo = geminiService.dialogoComerciante();

            javafx.application.Platform.runLater(() -> {
                txtDialogo.setText(
                        "COMERCIANTE\n" +
                                "-------------------------\n\n" + dialogo
                );
                lblEstado.setText("Diálogo generado");
            });
        }).start();
    }

    /**
     * Genera evento aleatorio.
     */
    @FXML
    private void handleEventoAleatorio() {
        lblEstado.setText("Generando evento con Gemini...");

        String[] eventos = {"tormenta", "río desbordado", "encuentro con nativos", "enfermedad"};
        String eventoAleatorio = eventos[(int)(Math.random() * eventos.length)];

        new Thread(() -> {
            String dialogo = geminiService.dialogoEventoAleatorio(eventoAleatorio);

            javafx.application.Platform.runLater(() -> {
                txtDialogo.setText(
                        "EVENTO ALEATORIO\n" +
                                "---------------------\n\n" + dialogo
                );
                lblEstado.setText("Evento generado");
            });
        }).start();
    }

    /**
     * Muestra estadísticas de enemigos.
     */
    @FXML
    private void handleEstadisticas() {
        int total = enemies.contEnemies();
        String lista = enemies.printList();

        txtDialogo.setText(
                "ESTADÍSTICAS DE ENEMIGOS\n" +
                        "-----------------------\n\n" +
                        "Total de enemigos activos: " + total + "\n\n" +
                        "Lista de tipos: " + (lista.isEmpty() ? "Ninguno" : lista)
        );
        lblEstado.setText("Estadísticas mostradas");
    }

    @FXML
    private void handleCerrar() {
        Stage stage = (Stage) lblEnemyInfo.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void handleDialogoSimulado() {
        txtDialogo.setText(
                "ENEMIGO ELIMINADO (Simulado)\n" +
                        "--------------------------\n\n" +
                        "Últimas palabras:\n" +
                        "\"El sendero a Oregón... es demasiado largo...\n" +
                        "Jamás llegarán... jamás...\""
        );
        lblEstado.setText("Diálogo simulado");
    }
}