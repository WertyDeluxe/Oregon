package org.oniteam.oregontrailfx.model;
import java.util.Random;

public class Spawner {
    private ListEnemy enemies;
    private int densidadMax;
    private int radioSeguro;
    private Random random;

    /**
     * Constructor corregido: inicializa enemies para evitar NullPointerException
     */
    public Spawner(int densidadMax, int radioSeguro) {
        this.densidadMax = densidadMax;
        this.radioSeguro = radioSeguro;
        this.random = new Random();
        this.enemies = new ListEnemy(); // ✅ FIX: Inicializar lista de enemigos
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
     * Genera enemigos respetando densidad máxima y radio seguro del jugador.
     *
     * @param map el escenario actual
     * @param p el jugador
     */
    public void tickSpawn(Scenario map, Player p){
        densidadMax = map.getCantMaxRespawnEnemies();

        if (enemies.contEnemies() >= densidadMax) {
            return;
        }

        SpawnPos pos = findValidPosition(map, p);
        if (pos == null) {
            return;
        }

        Enemy e = new Enemy(pos.getPosX(), pos.getPosY());
        enemies.addEnemy(e);
    }

    /**
     * Busca una posición válida fuera del radio seguro del jugador.
     *
     * @param map escenario actual
     * @param player jugador
     * @return posición válida o null si no encuentra en 30 intentos
     */
    private SpawnPos findValidPosition(Scenario map, Player player) {
        int intentos = 30;
        int width = map.getBoard().length;
        int height = map.getBoard()[0].length; // ✅ FIX: usar [0] para altura

        for (int i = 0; i < intentos; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            if (!isOutsideSafeRadius(x, y, player)) {
                continue;
            }

            return new SpawnPos(x, y);
        }

        return null;
    }

    /**
     * Verifica si una posición está fuera del radio seguro del jugador.
     */
    public boolean isOutsideSafeRadius(int x, int y, Player p) {
        int dx = x - p.getX();
        int dy = y - p.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);
        return dist >= radioSeguro;
    }
}