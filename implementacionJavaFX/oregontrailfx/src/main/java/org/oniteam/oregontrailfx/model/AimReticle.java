package org.oniteam.oregontrailfx.model;

public class AimReticle {
    private int x, y;

    public void update(Vec2 mousePos){
        this.x = (int)mousePos.getX();
        this.y = (int) mousePos.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
