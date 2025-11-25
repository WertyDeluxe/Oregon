package org.oniteam.oregontrailfx.model;

public class Achivement implements Comparable<Achivement>{
    private int difficulty;
    private String nameAchivement;
    private String description;

    public Achivement(int difficulty, String nameAchivement, String description) {
        this.difficulty = difficulty;
        this.nameAchivement = nameAchivement;
        this.description = description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getNameAchivement() {
        return nameAchivement;
    }

    public void setNameAchivement(String nameAchivement) {
        this.nameAchivement = nameAchivement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Achivement o) {
        if(difficulty == o.getDifficulty()){
            return 0;
        } else if (difficulty > o.getDifficulty()) {
            return 1;
        }else{
            return -1;
        }

    }

    @Override
    public String toString() {
        return  " | Logro: " +
                "dificultad: " + difficulty +
                ", nombre del logro: " + nameAchivement + '\'' +
                ", descripcion del logro" + description + '\'' +
                " | ";
    }
}
