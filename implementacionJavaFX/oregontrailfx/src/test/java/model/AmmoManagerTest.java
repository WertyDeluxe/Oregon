package model;

import org.junit.jupiter.api.Test;
import org.oniteam.oregontrailfx.model.AmmoManager;
import org.oniteam.oregontrailfx.model.FireControl;

import static org.junit.jupiter.api.Assertions.*;

public class AmmoManagerTest {

    private AmmoManager ammo;
    private FireControl fire;

    /**
     * Escenario 3: Configuración del sistema de munición y disparo
     * - AmmoManager inicializado con rifle=1, revolver=0
     * - FireControl configurado para usar el rifle
     *
     * Objetivo: Munición como recurso escaso y disparo bloqueado con 0
     */
    void setupStage3() {
        ammo = new AmmoManager(1, 0);
        fire = new FireControl("rifle", ammo, 6);
    }

    /**
     * Caso de prueba 1: Valida un disparo exitoso con munición disponible
     *
     * Objetivo: Munición como recurso escaso y disparo bloqueado con 0
     *
     * Valores de entrada:
     * - arma = "rifle"
     * - ammo.rifle = 1
     *
     * Resultado esperado:
     * - Dispara OK y queda ammo.rifle = 0
     */
    @Test
    void testShootWithAmmoAvailable() {
        // arrange
        setupStage3();

        // act
        boolean result = ammo.shoot("rifle");

        // assert
        assertTrue(result,
                "El disparo debe ser exitoso porque hay munición disponible (rifle=1)");
        assertEquals(0, ammo.getRifleAmmo(),
                "La munición del rifle debe quedar en 0 después del disparo");
    }

    /**
     * Caso de prueba 2: Valida  que no se puede disparar sin munición
     *
     * Objetivo: Munición como recurso escaso y disparo bloqueado con 0
     *
     * Valores de entrada:
     * - arma = "rifle"
     * - ammo.rifle = 0
     *
     * Resultado esperado:
     * - No dispara; HUD notifica "Sin munición"
     */
    @Test
    void testShootWithoutAmmo() {
        // arrange
        setupStage3();
        // Consumir la única munición disponible
        ammo.shoot("rifle");

        // act
        boolean result = ammo.shoot("rifle");

        // assert
        assertFalse(result,
                "El disparo debe fallar porque no hay munición disponible (rifle=0)");
        assertEquals(0, ammo.getRifleAmmo(),
                "La munición del rifle debe permanecer en 0");
        // Nota: La notificación del HUD "Sin munición" debería verificarse
        // mediante un mock o spy del sistema de notificaciones
    }

    /**
     * Caso de prueba 3: Valida el estado inicial del AmmoManager
     */
    @Test
    void testInitialAmmoState() {
        // arrange
        setupStage3();

        // act & assert
        assertEquals(1, ammo.getRifleAmmo(),
                "El rifle debe iniciar con 1 munición según setupStage3");
        assertEquals(0, ammo.getRevolverAmmo(),
                "El revolver debe iniciar con 0 municiones según setupStage3");
    }
}
