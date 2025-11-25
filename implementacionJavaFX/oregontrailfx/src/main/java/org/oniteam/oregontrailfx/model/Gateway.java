package org.oniteam.oregontrailfx.model;

public class Gateway {
    private final MapLoader source;
    private final MapLoader target;
    private final int portalX;
    private final int portalY;

    public Gateway(MapLoader source, MapLoader target, int portalX, int portalY){
        this.source = source;
        this.target = target;
        this.portalX = portalX;
        this.portalY = portalY;
    }

    public boolean transferIfCollides(Player player, SceneState state){
        if (player.getPosX() == portalX && player.getPosY() == portalY){
            state.setCurrentMap(target.getName());
            return true;
        }
        return false;
    }
}
