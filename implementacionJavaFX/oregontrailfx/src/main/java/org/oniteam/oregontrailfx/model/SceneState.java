package org.oniteam.oregontrailfx.model;

public class SceneState {
    private int vida;
    private int municionRifle;
    private int municionRevolver;
    private int capacidad;
    private String currentMap = "llanuras.map";

    public SceneState(int vida, int muniRifle, int muniRevolver, int capacidad){
        this.vida = vida;
        this.municionRifle = muniRifle;
        this.municionRevolver = muniRevolver;
        this.capacidad = capacidad;
    }

    public String getCurrentMap(){
        return currentMap;
    }

    public void setCurrentMap(String map){
        this.currentMap = map;
    }

    public int getVida(){
        return vida;
    }
    public int getMunicionRifle(){
        return municionRifle;
    }

    public int getMunicionRevolver(){
        return municionRevolver;
    }
}
