package org.oniteam.oregontrailfx.model;
import java.util.Random;

public class Spawner {
    private ListEnemy enemies;
    private int densidadMax;
    private  int radioSeguro;
    private Random random;

    public Spawner(int densidadMax, int radioSeguro) {
        this.densidadMax = densidadMax;
        this.radioSeguro = radioSeguro;
        this.random = new Random();
    }

    public ListEnemy getEnemies() {
        return enemies;
    }

    public void setEnemies(ListEnemy enemies) {
        this.enemies = enemies;
    }

    public int getDensidadMax() {
        return densidadMax;
    }

    public void setDensidadMax(int densidadMax) {
        this.densidadMax = densidadMax;
    }

    public int getRadioSeguro() {
        return radioSeguro;
    }

    public void setRadioSeguro(int radioSeguro) {
        this.radioSeguro = radioSeguro;
    }

    /**
     * este metodo sirve para primero validar si la cantidad maxima de enemigos es menor o igual
     * a densidad o cantidad maxima de enemigos, si no es asi, pasa a bucar una pos que sea valida
     * con el radio seguro y la densidad maxima
     * @param map el escenario el cual esta el jugador actualmente
     * @param p el player o el jugador
     */
    public void tickSpawn(Scenario map, Player p){
        densidadMax=map.getCantMaxRespawnEnemies();
        if (enemies.contEnemies() >= densidadMax) {
            return;
        }

        // buscar una celda válida en ese mapa
        SpawnPos pos = findValidPosition(map, p);
        if (pos == null) {
            return; // no hubo dónde
        }

        // crear y guardar en la lista enlazada interna
        Enemy e = new Enemy(pos.getPosX(), pos.getPosY());
        enemies.addEnemy(e); // o addLast, según tu lista
    }

    /**
     * este metodo sirve para validar si luego de que el random haya dado un valor cualquiera, es decir
     * , las posiciones x,y,  valide si  esta fuera del rango seguro,  y si esta fuera pues crea
     * la nueva pos con la clase spawnpos y retorna ese nuevo objecto
     * @param map
     * @param player
     * @return
     */
    private SpawnPos findValidPosition(Scenario map, Player player) {
        int intentos = 30;
        int width = map.getBoard().length;
        int height = map.getBoard().length;

        for (int i = 0; i < intentos; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);



            // está dentro del radio seguro -> no sirve
            if (!isOutsideSafeRadius(x, y, player)) {
                continue; // esto sirve para pasar a la siguiente iteracion, y sola la salta si esas posiciones estan fuera del rango seguro del jugador
            }

            return new SpawnPos(x, y);
        }

        return null;
    }

    public boolean isOutsideSafeRadius(int x, int y, Player p) {
        int dx = x - p.getX();
        int dy = y - p.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);
        return dist >= radioSeguro;
    }
}
