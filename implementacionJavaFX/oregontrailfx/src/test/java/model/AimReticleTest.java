package model;

import org.junit.jupiter.api.Test;
import org.oniteam.oregontrailfx.model.AimReticle;
import org.oniteam.oregontrailfx.model.FireControl;
import org.oniteam.oregontrailfx.model.Mouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AimReticleTest {

    private AimReticle ret;
    private FireControl fire;

    void setupStage9() {
        ret = new AimReticle();
        fire = new FireControl("rifle");
        Mouse.setPos(100, 200);
    }

    @Test
    void testReticleUpdatesToMousePosition() {
        // arrange
        setupStage9();

        // act
        ret.update(Mouse.getPos());

        // assert
        assertEquals(100, ret.getX(), "La retícula debe ubicarse en X=100");
        assertEquals(200, ret.getY(), "La retícula debe ubicarse en Y=200");
    }

    @Test
    void testAimOrientationTowardsMouse() {
        // arrange
        setupStage9();
        ret.update(Mouse.getPos());

        // act
        double angle = fire.aimAt(ret);

        // assert
        assertTrue(angle >= 0 && angle <= 360,
                "El ángulo de orientación debe ser válido (0–360 grados)");
    }
}
