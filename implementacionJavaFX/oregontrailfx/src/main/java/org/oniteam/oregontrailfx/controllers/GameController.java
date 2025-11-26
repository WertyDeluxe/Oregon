package org.oniteam.oregontrailfx.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import org.oniteam.oregontrailfx.model.*;

public class GameController {

    @FXML private Canvas gameCanvas;
    @FXML private Label lblHealth;
    @FXML private Label lblAmmo;
    @FXML private Label lblScenario;
    @FXML private Label lblInventorySummary;

    private GraphicsContext gc;
    private GameManager gameManager;
    private Player player;
    private Scenario currentScenario;
    private MapLoader currentMap;
    private MovementController movementController;
    private Inventory inventory;
    private AmmoManager ammoManager;
    private Spawner spawner;
    private ListEnemy enemies;

    // Estados de entrada
    private boolean up, down, left, right, shooting;

    // Sprites
    private Image[] heroIdle;
    private Image[] heroRun;
    private int heroFrame = 0;
    private long lastFrameTime = 0;
    private long frameDurationNanos = 150_000_000L;

    // Fondos
    private Image bgLlanura;
    private Image bgMountain;
    private Image bgRiver;

    // Enemigo simple (imagen)
    private Image enemyImg;

    private AnimationTimer loop;

    @FXML
    private void initialize() {
        gc = gameCanvas.getGraphicsContext2D();
        loadSprites();
        initModel();
        initLoop();
    }

    private void loadSprites() {
        System.out.println("Cargando sprites");

        try {
            // idle
            heroIdle = new Image[4];
            for (int i = 0; i < 4; i++) {
                heroIdle[i] = cargarImagen("/images/hero/idle/hero-idle-0" + i + ".png");
            }

            // corriendo
            heroRun = new Image[6];
            for (int i = 0; i < 6; i++) {
                heroRun[i] = cargarImagen("/images/hero/run/hero-run-0" + i + ".png");
            }

            // Cargar fondos
            bgLlanura = cargarImagen("/images/escenario/llanura.png");
            bgMountain = cargarImagen("/images/escenario/mountain.png");
            bgRiver = cargarImagen("/images/escenario/river.png");

            // Cargar enemigo
            enemyImg = cargarImagen("/images/enemy/enemy.png");

            System.out.println("Sprites cargados correctamente");

        } catch (Exception e) {
            System.err.println("Error cargando sprites: " + e.getMessage());
            e.printStackTrace();
            crearImagenesPlaceholder();
        }
    }

    /**
     * Carga una imagen con manejo de errores.
     * Si no existe, retorna un placeholder.
     */
    private Image cargarImagen(String ruta) {
        try {
            var stream = getClass().getResourceAsStream(ruta);
            if (stream == null) {
                System.err.println("Imagen no encontrada: " + ruta);
                return crearImagenPlaceholder();
            }
            return new Image(stream);
        } catch (Exception e) {
            System.err.println("Error cargando: " + ruta);
            return crearImagenPlaceholder();
        }
    }

    /**
     * Crea una imagen placeholder de color sólido.
     */
    private Image crearImagenPlaceholder() {
        javafx.scene.image.WritableImage img = new javafx.scene.image.WritableImage(32, 32);
        javafx.scene.image.PixelWriter pw = img.getPixelWriter();

        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                pw.setColor(x, y, javafx.scene.paint.Color.LIGHTBLUE);
            }
        }

        return img;
    }

    /**
     * Crea imágenes placeholder para todo si fallan las cargas.
     */
    private void crearImagenesPlaceholder() {
        System.out.println("Usando imágenes placeholder...");

        Image placeholder = crearImagenPlaceholder();

        heroIdle = new Image[4];
        heroRun = new Image[6];

        for (int i = 0; i < 4; i++) heroIdle[i] = placeholder;
        for (int i = 0; i < 6; i++) heroRun[i] = placeholder;

        bgLlanura = placeholder;
        bgMountain = placeholder;
        bgRiver = placeholder;
        enemyImg = placeholder;
    }

    private void initModel() {
        gameManager = GameManager.getInstance();
        player = gameManager.getPlayer();

        // Si player es null, crear uno por defecto
        if (player == null) {
            player = new Player("Viajero", "Carpintero");
            gameManager.setJugador(player);
        }

        // Inicializar escenario
        currentScenario = gameManager.getCurrentScenario();
        if (currentScenario == null) {
            currentScenario = new Scenario(new int[20][20], TypeScenarios.START);
            gameManager.setCurrentScenario(currentScenario);
        }

        // Inicializar mapa
        currentMap = MapLoader.of("llanuras.map");

        // Inicializar inventario
        inventory = new Inventory(20);

        // Inicializar munición
        ammoManager = new AmmoManager(50, 30);

        // Inicializar spawner de enemigos
        spawner = new Spawner(5, 3);
        enemies = spawner.getEnemies();

        // Controlador de movimiento
        movementController = new MovementController(currentMap, player, currentScenario);
    }

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

    @FXML
    private void onKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case W, UP -> up = true;
            case S, DOWN -> down = true;
            case A, LEFT -> left = true;
            case D, RIGHT -> right = true;
            case SPACE -> shooting = true;
            case I -> abrirInventario();
            case ESCAPE -> pausarJuego();
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

    private void update(long now) {
        // Verificar condiciones de victoria/derrota
        if (player.getVida() <= 0) {
            gameOver();
            return;
        }

        if (gameManager.isJuegoGanado()) {
            victoria();
            return;
        }

        // Movimiento del jugador
        if (up) movementController.moveUp();
        if (down) movementController.moveDown();
        if (left) movementController.moveLeft();
        if (right) movementController.moveRight();

        // Disparo
        if (shooting && ammoManager.hasAmmo("rifle")) {
            ammoManager.shoot("rifle");
            // Aquí podrías agregar lógica de colisión con enemigos
        }

        // Spawn de enemigos
        spawner.tickSpawn(currentScenario, player);

        // Actualizar IA de enemigos
        NodeEnemy current = enemies.getFirst();
        while (current != null) {
            Enemy e = current.getData();
            EnemyAI ai = new EnemyAI(5.0);
            ai.update(e, player);

            // Verificar colisión con jugador
            if (e.getX() == player.getX() && e.getY() == player.getY()) {
                player.damage(1);
            }

            current = current.getNext();
        }

        // Animación
        if (now - lastFrameTime > frameDurationNanos) {
            heroFrame = (heroFrame + 1) % (isMoving() ? heroRun.length : heroIdle.length);
            lastFrameTime = now;
        }

        updateHUD();
    }

    private boolean isMoving() {
        return up || down || left || right;
    }

    private void updateHUD() {
        lblHealth.setText("Vida: " + player.getVida());
        lblAmmo.setText("Balas: " + ammoManager.getRifleAmmo());
        lblScenario.setText("Ubicación: " + currentScenario.getType().name());

        // Resumen de inventario
        int itemCount = inventory.size();
        lblInventorySummary.setText("Items: " + itemCount + "/" + 20);
    }

    private void render() {
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        // Fondo según escenario
        Image bg = switch (currentScenario.getType()) {
            case START -> bgLlanura;
            case ROAD -> bgMountain;
            case RIVER -> bgRiver;
            default -> bgLlanura;
        };

        if (bg != null) {
            gc.drawImage(bg, 0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        }

        // Dibujar enemigos
        NodeEnemy current = enemies.getFirst();
        while (current != null) {
            Enemy e = current.getData();
            if (enemyImg != null) {
                gc.drawImage(enemyImg, e.getX() * 32, e.getY() * 32, 32, 32);
            }
            current = current.getNext();
        }

        // Dibujar jugador
        Image sprite = isMoving()
                ? heroRun[heroFrame]
                : heroIdle[heroFrame % heroIdle.length];

        if (sprite != null) {
            gc.drawImage(sprite, player.getX() * 32, player.getY() * 32, 48, 48);
        }
    }

    private void abrirInventario() {
        // TODO: Abrir ventana de inventario
        System.out.println("Abriendo inventario...");
    }

    private void pausarJuego() {
        loop.stop();
        // TODO: Mostrar menú de pausa
        System.out.println("Juego pausado");
    }

    private void gameOver() {
        loop.stop();
        gameManager.terminarJuego(false);
        cargarVista("/views/gameover.fxml", "Game Over");
    }

    private void victoria() {
        loop.stop();
        gameManager.terminarJuego(true);
        cargarVista("/views/victoria.fxml", "¡Victoria!");
    }

    private void cargarVista(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = (Stage) gameCanvas.getScene().getWindow();
            Scene scene = new Scene(root, 900, 700);
            stage.setTitle(titulo);
            stage.setScene(scene);
        } catch (Exception e) {
            System.err.println("Error cargando vista: " + fxml);
            e.printStackTrace();
        }
    }
}