package model;


import org.junit.jupiter.api.Test;
import org.oniteam.oregontrailfx.model.MapLoader;
import org.oniteam.oregontrailfx.model.MovementController;
import org.oniteam.oregontrailfx.model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovementControllerTest {

    private MovementController mc;
    private MapLoader map;
    private Player p;

    /**
     * Escenario 1: Configuración inicial del controlador de movimiento
     * - Mapa: llanuras.map
     * - Jugador en posición (2,2)
     * - Celda (2,1) está bloqueada (pared)
     * - Celda (3,2) está libre
     * - Celda (3,3) está libre
     */
    void setupStage1() {
        map = MapLoader.of("llanuras.map");
        p = new Player(2, 2);
        mc = new MovementController(map, p);
    }

    /**
     * Caso de prueba 1: Validar que el jugador NO se mueve hacia arriba
     * cuando la celda está bloqueada (pared)
     *
     * Valores de entrada:
     * - posIn=(2,2)
     * - celda(2,1)=pared (bloqueada)
     *
     * Resultado esperado:
     * - posición final=(2,2) - no se mueve porque está bloqueada
     */
    @Test
    void testMoveUpWithBlockedCell() {
        // arrange
        setupStage1();

        // act
        mc.moveUp();

        // assert
        assertEquals(2, p.getPosX(),
                "La posición X debe permanecer en 2 porque la celda superior está bloqueada");
        assertEquals(2, p.getPosY(),
                "La posición Y debe permanecer en 2 porque la celda superior está bloqueada");
    }

    /**
     * Caso de prueba 2: Validar que el jugador SE MUEVE hacia la derecha
     * cuando la celda está libre
     *
     * Valores de entrada:
     * - posIn=(2,2)
     * - celda(3,2)=libre
     *
     * Resultado esperado:
     * - posición final=(3,2)
     */
    @Test
    void testMoveRightToFreeCell() {
        // arrange
        setupStage1();

        // act
        mc.moveRight();

        // assert
        assertEquals(3, p.getPosX(),
                "La posición X debe cambiar a 3 porque la celda derecha está libre");
        assertEquals(2, p.getPosY(),
                "La posición Y debe permanecer en 2");
    }

    /**
     * Caso de prueba 3: Validar que el jugador SE MUEVE hacia abajo
     * cuando la celda está libre
     *
     * Valores de entrada:
     * - posIn=(3,2)
     * - celda(3,3)=libre
     *
     * Resultado esperado:
     * - posición final=(3,3)
     */
    @Test
    void testMoveDownToFreeCell() {
        // arrange
        setupStage1();

        // act
        mc.moveDown();

        // assert
        assertEquals(3, p.getPosX(),
                "La posición X debe cambiar a 3");
        assertEquals(3, p.getPosY(),
                "La posición Y debe cambiar a 3 porque la celda inferior está libre");
    }
}
