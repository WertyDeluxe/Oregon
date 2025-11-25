package org.oniteam.oregontrailfx.model;

public class Vec2 {
    private double x;
    private double y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 add(double dx, double dy) {
        return new Vec2(this.x + dx, this.y + dy);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public static final Vec2 ZERO = new Vec2(0, 0);
}
