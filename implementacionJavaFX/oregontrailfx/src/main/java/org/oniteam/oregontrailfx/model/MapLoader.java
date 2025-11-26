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

    /**
     * Verifica si una celda está bloqueada.
     * NOTA: Los límites del canvas se verifican en MovementController,
     * aquí solo verificamos obstáculos internos del mapa.
     */
    public boolean isBlocked(int x, int y){
        // Ejemplo de celdas bloqueadas en llanuras
        if ("llanuras.map".equals(name)) {
            // Agregar algunos obstáculos
            return (x == 2 && y == 1) ||  // Roca
                    (x == 7 && y == 5) ||  // Árbol
                    (x == 10 && y == 3);   // Arbusto
        }

        return false; // Resto del mapa es transitable
    }
}