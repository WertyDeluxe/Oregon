package org.oniteam.oregontrailfx.model;

public class SpawnPos {
    private int posY;
    private int posX;

    public SpawnPos(int posY, int posX) {
        this.posY = posY;
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }
}
