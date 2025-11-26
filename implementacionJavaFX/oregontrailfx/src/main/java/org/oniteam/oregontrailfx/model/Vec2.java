package org.oniteam.oregontrailfx.model;

public class Vec2 {
    private int x;
    private int y;

    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 add(int dx, int dy) {
        return new Vec2(this.x + dx, this.y + dy);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static final Vec2 ZERO = new Vec2(0, 0);
}
