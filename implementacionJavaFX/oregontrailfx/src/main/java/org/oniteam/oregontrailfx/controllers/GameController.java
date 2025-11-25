package org.oniteam.oregontrailfx.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import org.oniteam.oregontrailfx.model.*;

public class GameController {

    @FXML
    private Canvas gameCanvas;

    @FXML
    private Label lblHealth;

    @FXML
    private Label lblAmmo;

    @FXML
    private Label lblScenario;

    @FXML
    private Label lblInventorySummary;

    private GraphicsContext gc;

    // Instancia de GameManager, que maneja el estado global del juego
    private GameManager gameManager;
    private Player player;
    private Scenario currentScenario;
    private MovementController movementController;
    private FireControl fireControl;
    private Inventory inventory;

    // Estados de entrada de teclado
    private boolean up, down, left, right, shooting;

    // Sprites para animar al jugador
    private Image[] heroIdle;
    private Image[] heroRun;
    private int heroFrame = 0;
    private long lastFrameTime = 0;
    private long frameDurationNanos = 150_000_000L; // 150ms para cambiar el frame

    // Fondos del escenario
    private Image bgLlanura;
    private Image bgMountain;
    private Image bgRiver;

    private AnimationTimer loop;

    @FXML
    private void initialize() {
        gc = gameCanvas.getGraphicsContext2D();
        loadSprites();
        initModel();
        initLoop();
    }

    // Cargar los sprites del jugador
    private void loadSprites() {
        // Carga las imágenes del jugador (idle y correr)
        heroIdle = new Image[]{
                new Image(getClass().getResourceAsStream("/images/hero/idle/hero-idle-00.png")),
                new Image(getClass().getResourceAsStream("/images/hero/idle/hero-idle-01.png")),
                new Image(getClass().getResourceAsStream("/images/hero/idle/hero-idle-02.png")),
                new Image(getClass().getResourceAsStream("/images/hero/idle/hero-idle-03.png"))
        };

        heroRun = new Image[]{
                new Image(getClass().getResourceAsStream("/images/hero/run/hero-run-00.png")),
                new Image(getClass().getResourceAsStream("/images/hero/run/hero-run-01.png")),
                new Image(getClass().getResourceAsStream("/images/hero/run/hero-run-02.png")),
                new Image(getClass().getResourceAsStream("/images/hero/run/hero-run-03.png")),
                new Image(getClass().getResourceAsStream("/images/hero/run/hero-run-04.png")),
                new Image(getClass().getResourceAsStream("/images/hero/run/hero-run-05.png"))
        };

        // Fondos para cada tipo de escenario
        bgLlanura = new Image(getClass().getResourceAsStream("/images/escenario/llanura.png"));
        bgMountain = new Image(getClass().getResourceAsStream("/images/escenario/mountain.png"));
        bgRiver = new Image(getClass().getResourceAsStream("/images/escenario/river.png"));
    }

    // Inicialización del modelo y las instancias necesarias
    private void initModel() {
        gameManager = GameManager.getInstance(); // Obtener instancia de GameManager
        player = gameManager.getPlayer(); // Obtener el jugador
        currentScenario = gameManager.getCurrentScenario(); // Obtener el escenario actual
        inventory = player.getInventory(); // Obtener el inventario del jugador

        movementController = new MovementController(currentScenario); // Controlador de movimiento
        fireControl = new FireControl(gameManager, player); // Controlador de disparos
    }

    // Inicialización del bucle de animación
    private void initLoop() {
        loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
                render();
            }
        };
        loop.start();
    }

    // Métodos para manejar las teclas presionadas y liberadas
    @FXML
    private void onKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case W, UP -> up = true;
            case S, DOWN -> down = true;
            case A, LEFT -> left = true;
            case D, RIGHT -> right = true;
            case SPACE -> shooting = true;
        }
    }

    @FXML
    private void onKeyReleased(KeyEvent e) {
        switch (e.getCode()) {
            case W, UP -> up = false;
            case S, DOWN -> down = false;
            case A, LEFT -> left = false;
            case D, RIGHT -> right = false;
            case SPACE -> shooting = false;
        }
    }

    // Actualiza el estado del juego, incluyendo movimiento, disparo y lógica general
    private void update(long now) {
        // Dirección del jugador según las teclas presionadas
        Vec2 dir = new Vec2(0, 0);
        if (up) dir = dir.add(0, -1);
        if (down) dir = dir.add(0, 1);
        if (left) dir = dir.add(-1, 0);
        if (right) dir = dir.add(1, 0);

        // Mueve al jugador si hay dirección
        if (!dir.equals(Vec2.ZERO)) {
            movementController.movePlayer(player, dir, currentScenario);
        }

        // Si se presiona espacio, disparar
        if (shooting) {
            fireControl.playerShoot(player);
        }

        // Actualizar el escenario y el jugador
        gameManager.update(currentScenario, player);

        // Animación simple: cambiar de frame
        if (now - lastFrameTime > frameDurationNanos) {
            heroFrame = (heroFrame + 1) % (isMoving() ? heroRun.length : heroIdle.length);
            lastFrameTime = now;
        }

        updateHUD();
    }

    // Verifica si el jugador está en movimiento
    private boolean isMoving() {
        return up || down || left || right;
    }

    // Actualiza los valores del HUD (vida, munición, escenario, inventario)
    private void updateHUD() {
        lblHealth.setText("Vida: " + player.getVida());
        lblAmmo.setText("Dinero: " + String.format("%.2f", player.getDinero()));
        lblScenario.setText("Escenario: " + currentScenario.getTypeScenario().name());

        int food = inventory.countFood();
        int meds = inventory.countMedicines();
        int bullets = inventory.countAmmo();
        lblInventorySummary.setText(
                "Comida: " + food + " | Meds: " + meds + " | Balas: " + bullets
        );
    }

    // Renderiza el contenido del juego: fondo, jugador, enemigos, etc.
    private void render() {
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        // Fondo según el tipo de escenario
        switch (currentScenario.getTypeScenario()) {
            case PLAINS -> gc.drawImage(bgLlanura, 0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
            case MOUNTAINS -> gc.drawImage(bgMountain, 0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
            case RIVER -> gc.drawImage(bgRiver, 0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        }

        // Dibuja el jugador
        Vec2 pos = player.getPosition();
        Image sprite = isMoving()
                ? heroRun[heroFrame]
                : heroIdle[heroFrame % heroIdle.length];

        gc.drawImage(sprite, pos.getX(), pos.getY());

        // Renderiza enemigos y otras entidades en el escenario
        currentScenario.renderEntities(gc);
    }
}
