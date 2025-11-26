package org.oniteam.oregontrailfx.model;

public class MovementController {
    private final MapLoader map;
    private final Player player;
    private final int maxTilesX; // ⭐ Límite horizontal
    private final int maxTilesY; // ⭐ Límite vertical

    public MovementController(MapLoader map, Player player, int maxTilesX, int maxTilesY) {
        this.map = map;
        this.player = player;
        this.maxTilesX = maxTilesX;
        this.maxTilesY = maxTilesY;

        System.out.println("🛡️ MovementController inicializado con límites: " + maxTilesX + "x" + maxTilesY);
    }

    public void moveUp(){
        int nx = player.getPosX();
        int ny = player.getPosY() - 1;

        // ⭐ Verificar límite superior
        if (ny < 0) {
            System.out.println("🚫 Límite superior alcanzado");
            return;
        }

        if (!map.isBlocked(nx, ny)){
            player.setPosition(nx, ny);
            System.out.println("↑ Jugador movido a: (" + nx + ", " + ny + ")");
        } else {
            System.out.println("❌ Movimiento bloqueado en: (" + nx + ", " + ny + ")");
        }
    }

    public void moveDown(){
        int nx = player.getPosX();
        int ny = player.getPosY() + 1;

        // ⭐ Verificar límite inferior (Canvas abajo)
        if (ny >= maxTilesY) {
            System.out.println("🚫 Límite inferior alcanzado");
            return;
        }

        if (!map.isBlocked(nx, ny)){
            player.setPosition(nx, ny);
            System.out.println("↓ Jugador movido a: (" + nx + ", " + ny + ")");
        } else {
            System.out.println("❌ Movimiento bloqueado en: (" + nx + ", " + ny + ")");
        }
    }

    public void moveLeft(){
        int nx = player.getPosX() - 1;
        int ny = player.getPosY();

        // ⭐ Verificar límite izquierdo
        if (nx < 0) {
            System.out.println("🚫 Límite izquierdo alcanzado");
            return;
        }

        if (!map.isBlocked(nx, ny)){
            player.setPosition(nx, ny);
            System.out.println("← Jugador movido a: (" + nx + ", " + ny + ")");
        } else {
            System.out.println("❌ Movimiento bloqueado en: (" + nx + ", " + ny + ")");
        }
    }

    public void moveRight(){
        int nx = player.getPosX() + 1;
        int ny = player.getPosY();

        // ⭐ Verificar límite derecho
        if (nx >= maxTilesX) {
            System.out.println("🚫 Límite derecho alcanzado");
            return;
        }

        if (!map.isBlocked(nx, ny)){
            player.setPosition(nx, ny);
            System.out.println("→ Jugador movido a: (" + nx + ", " + ny + ")");
        } else {
            System.out.println("❌ Movimiento bloqueado en: (" + nx + ", " + ny + ")");
        }
    }
}