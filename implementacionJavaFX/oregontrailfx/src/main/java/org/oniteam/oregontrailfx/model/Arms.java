package org.oniteam.oregontrailfx.model;

public class Arms {

    private int distance;
    private double damage;
    private int spaceInventory;
    private TypeArms typearm;


    public Arms(int distance, double damage, int spaceInventory, TypeArms typearm) {
        this.distance = distance;
        this.damage = damage;
        this.spaceInventory = spaceInventory;
        this.typearm = typearm;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public int getSpaceInventory() {
        return spaceInventory;
    }

    public void setSpaceInventory(int spaceInventory) {
        this.spaceInventory = spaceInventory;
    }

    public TypeArms getTypearm() {
        return typearm;
    }

    public void setTypearm(TypeArms typearm) {
        this.typearm = typearm;
    }
}
