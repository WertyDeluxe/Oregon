package model;

import org.junit.jupiter.api.Test;
import org.oniteam.oregontrailfx.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class SpawnerTest {

    private Scenario map;
    private Spawner sp;
    private Player p;
    private ListEnemy enemies;


    void setupStage6() {
        int [][] board = new int [30][30];
        map = new Scenario(board, TypeScenarios.START );
        sp = new Spawner(5, 3);    // capacidad o densidad maximaMax=5, radioSeguro=3
        p = new Player(10, 10);
        enemies = new ListEnemy();
        sp.setEnemies(enemies);
    }


    @Test
    void testNoSpawnSiAlcanzaDensidadMaxima() {
        // arrange
        setupStage6();
        map.setCantMaxRespawnEnemies(5);
        // Preparamos 5 enemigos (densidadMax=5) lejos del jugador
        sp.getEnemies().addEnemy(new Enemy(0, 0));
        sp.getEnemies().addEnemy(new Enemy(20, 0));
        sp.getEnemies().addEnemy(new Enemy(0, 20));
        sp.getEnemies().addEnemy(new Enemy(25, 25));
        sp.getEnemies().addEnemy(new Enemy(30, 5));
        int antes =sp.getEnemies().contEnemies();

        // act
        sp.tickSpawn(map,p); // intenta spawnear si hay cupo

        // assert
        assertEquals(antes, sp.getEnemies().contEnemies()); //Con densidad al máximo, no debe agregarse ningún enemigo nuevo
    }

    @Test
    void testSpawneaFueraDeRadioSeguroSiHayCapacidad() {
        // arrange
        setupStage6();
        int antes = enemies.contEnemies();

        // act: dar varias oportunidades para que ocurra al menos un spawn
        for (int i = 0; i < 10 && enemies.contEnemies() == antes; i++) {
            sp.tickSpawn(map, p);
        }

        // assert
        assertTrue(enemies.contEnemies() > antes,
                "Debe spawnear al menos un enemigo cuando hay capacidad disponible");
        NodeEnemy current=enemies.getFirst();
        while (current!=null){
            boolean dist = sp.isOutsideSafeRadius(current.getData().getX(), current.getData().getY(), p);//posiciones del "e" enemigo y el jugador o player "p", calcula si la distancia es igual o mayor a 3
            assertTrue(dist);//si la distancia entre ese enemigo que esta en la lista enlazada es igual o mayor a 3, la prueba es exitosa, y el metodo isOutsideSafeRadius, retorna un true

        }
    }
}
