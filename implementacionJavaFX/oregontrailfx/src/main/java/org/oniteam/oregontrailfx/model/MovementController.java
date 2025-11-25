package org.oniteam.oregontrailfx.model;

public class MovementController {
    private final MapLoader map;
    private final Player player;
    private Scenario scenario;

    public MovementController(MapLoader map, Player player, Scenario scenario) {
        this.map = map;
        this.player = player;
        this.scenario = scenario;
    }

    public void moveUp(){
        int nx = player.getPosX();
        int ny = player.getPosY() - 1;
        if (!map.isBlocked(nx, ny)){
            player.setPosition(nx, ny);
        }
    }

    public void moveDown(){
        int nx = player.getPosX();
        int ny = player.getPosY() + 1;
        if (!map.isBlocked(nx, ny)){
            player.setPosition(nx, ny);
        }
    }

    public void moveLeft(){
        int nx = player.getPosX() - 1;
        int ny = player.getPosY();
        if (!map.isBlocked(nx, ny)){
            player.setPosition(nx, ny);
        }
    }

    public void moveRight(){
        int nx = player.getPosX() + 1;
        int ny = player.getPosY();
        if (!map.isBlocked(nx, ny)){
            player.setPosition(nx, ny);
        }
    }
    public void movePlayer(Player player, Vec2 direction, Scenario scenario) {
        player.setPosition(new Vec2(player.getPosition().getX() + direction.getX(),
                player.getPosition().getY() + direction.getY()));
    }
}
