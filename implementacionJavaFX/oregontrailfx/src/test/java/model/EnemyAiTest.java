package model;

import org.junit.jupiter.api.Test;
import org.oniteam.oregontrailfx.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnemyAiTest {

    private EnemyAI ai;
    private EnemyAttack atk;
    private Player p;
    private Enemy e;

    void setupStage7() {
        ai = new EnemyAI(8); // rangoVision = 8
        atk = new EnemyAttack(1, 800); // daño=1, cadenciaMs=800
        p = new Player(5, 5, 3); // vida=3
        e = new Enemy(0, 0);
    }

    @Test
    void testEnemyPersigueJugadorEnRango() {
        // arrange
        setupStage7();

        // act
        ai.update(e, p); //Cambia a PERSEGUIR

        // assert
        assertEquals(EnemyState.PERSEGUIR, e.getState(),
                "El enemigo debe perseguir si el jugador está dentro del rango de visión");
        assertTrue(e.getDistanceTo(p) < 6,
                "El enemigo debe reducir la distancia al jugador");
    }

    @Test
    void testEnemyAtacaReduceVidaJugador() {
        // arrange
        setupStage7();

        // act
        e.setPosition(5, 5);
        boolean result = atk.tryAttack(e, p);

        // assert
        assertTrue(result, "El ataque debería ser exitoso al estar en rango");
        assertEquals(2, p.getVida(), "La vida del jugador debe reducirse en 1 tras el ataque");
    }
}
