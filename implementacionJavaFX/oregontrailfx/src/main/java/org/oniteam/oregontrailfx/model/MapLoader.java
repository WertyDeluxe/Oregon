package org.oniteam.oregontrailfx.model;

public class MapLoader {
    private final String name;

    private MapLoader(String name){
        this.name = name;
    }

    public static MapLoader of(String name){
        return new MapLoader(name);
    }

    public String getName(){
        return name;
    }

    public boolean isBlocked(int x, int y){
        if ("llanuras.map".equals(name)) {
            return (x == 2 && y == 1);
        }
        return false;
    }
}
