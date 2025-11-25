package model;

import org.junit.jupiter.api.Test;
import org.oniteam.oregontrailfx.model.Gateway;
import org.oniteam.oregontrailfx.model.MapLoader;
import org.oniteam.oregontrailfx.model.Player;
import org.oniteam.oregontrailfx.model.SceneState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GatewaysTest {

    private MapLoader plains;
    private MapLoader mountains;
    private SceneState state;
    private Gateway g;
    private Player player;

    /**
     * Escenario 2: Configuración de transición entre escenarios (Gateways)
     * - Mapa inicial: llanuras.map
     * - Mapa destino: montanas.map
     * - Estado del jugador: vida=3, municionRifle=5, municionRevolver=10
     * - Gateway ubicado en posición (9,0) conectando ambos mapas
     */
    void setupStage2() {
        plains = MapLoader.of("llanuras.map");
        mountains = MapLoader.of("montanas.map");
        state = new SceneState(3, 5, 10, 5);
        g = new Gateway(plains, mountains, 9, 0);
        player = new Player(0, 0);
    }

    /**
     * Caso de prueba 1: Validar transición exitosa entre escenarios preservando estado
     *
     * Objetivo: Transición entre escenarios preservando estado y bloqueando saltos indebidos
     *
     * Valores de entrada:
     * - playerPos = portal(9,0)
     * - state(vida=3, muniR=5, muniRev=10)
     *
     * Resultado esperado:
     * - Cambia a "montanas.map" y mantiene vida=3, muniR=5, muniRev=10
     */
    @Test
    void testTransferIfCollidesWithValidTransition() {
        // arrange
        setupStage2();
        player.setPosition(9, 0);

        // act
        g.transferIfCollides(player, state);

        // assert
        assertEquals("montanas.map", state.getCurrentMap(),
                "Debe cambiar a 'montanas.map' porque el jugador está en el portal");
        assertEquals(3, state.getVida(),
                "La vida debe mantenerse en 3 después de la transición");
        assertEquals(5, state.getMunicionRifle(),
                "La munición del rifle debe mantenerse en 5 después de la transición");
        assertEquals(10, state.getMunicionRevolver(),
                "La munición del revolver debe mantenerse en 10 después de la transición");
    }

    /**
     * Caso de prueba 2: Validar que se deniega transición a escenario no adyacente
     *
     * Objetivo: Transición entre escenarios preservando estado y bloqueando saltos indebidos
     *
     * Valores de entrada:
     * - Intento de salto "llanuras - rio" (no adyacente)
     *
     * Resultado esperado:
     * - Transición denegada
     */
    @Test
    void testTransferIfCollidesWithInvalidTransition() {
        // arrange
        setupStage2();
        player.setPosition(5, 5);

        // act
        boolean transitionOccurred = g.transferIfCollides(player, state);

        // assert
        assertFalse(transitionOccurred,
                "La transición debe ser denegada porque el jugador no está en el portal");
        assertEquals("llanuras.map", state.getCurrentMap(),
                "Debe permanecer en el mapa actual porque no hay colisión con el gateway");
        assertEquals(3, state.getVida(),
                "La vida debe mantenerse intacta");
    }

}
