package model;

import org.junit.jupiter.api.Test;
import org.oniteam.oregontrailfx.model.AmmoManager;
import org.oniteam.oregontrailfx.model.FireControl;
import org.oniteam.oregontrailfx.model.Reloading;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReloadingTest {
    AmmoManager ammo = new AmmoManager(rifle=0, revolver=6);
    Reloading re = new Reloading(auto=false, tRecargaMs=2000);
    FireControl fire = new FireControl("revolver", ammo, re);

    void setupStage4() {
        AmmoManager ammo = new AmmoManager(0, 6);          // rifle=0, revolver=6
        Reloading re = new Reloading(false, 2000);         // auto=false, tRecargaMs=2000
        FireControl fire = new FireControl("revolver", ammo, re);

    }
    @Test
    void test1reloadManualBlockTheShot() {
        setupStage4();

        // act: comienza la recarga manual
        re.startReload("revolver");
        boolean puedeDispararDurante = fire.shoot();

        // assert
        assertFalse(puedeDispararDurante,
                "El disparo debe estar bloqueado durante la recarga manual");
    }

    @Test
    void testRecargaAutomaticaBloqueaDisparo() {
        // arrange
        ammo = new AmmoManager(0, 0);        // sin munición
        re = new Reloading(true, 2000);      // autoReload habilitado
        fire = new FireControl("revolver", ammo, re);

        // act: intenta disparar sin munición → debería activar auto recarga
        boolean disparoInicial = fire.shoot();
        boolean enRecarga = re.isReloading();

        // assert
        assertFalse(disparoInicial); ///"No debe disparar sin munición"
        assertTrue(enRecarga);///Debe iniciar recarga automática al detectar ammo=0


    }

}
