package org.oniteam.oregontrailfx.model;

public class Scenario {

    private int[][] board;
    private TypeScenarios type;
    private int cantMaxRespawnEnemies;
    private int cantResources;

    public Scenario(int[][] board, TypeScenarios type) {
        this.board = board;
        this.type = type;
        this.cantMaxRespawnEnemies = 0;
        this.cantResources = 0;
    }

    public Scenario() {
        this(new int[10][10], TypeScenarios.START);
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public TypeScenarios getType() {
        return type;
    }

    public void setType(TypeScenarios type) {
        this.type = type;
    }

    public int getCantMaxRespawnEnemies() {
        return cantMaxRespawnEnemies;
    }

    public void setCantMaxRespawnEnemies(int cantMaxRespawnEnemies) {
        this.cantMaxRespawnEnemies = cantMaxRespawnEnemies;
    }

    public int getCantResources() {
        return cantResources;
    }

    public void setCantResources(int cantResources) {
        this.cantResources = cantResources;
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "type=" + type +
                ", cantMaxRespawnEnemies=" + cantMaxRespawnEnemies +
                ", cantResources=" + cantResources +
                '}';
    }


}
