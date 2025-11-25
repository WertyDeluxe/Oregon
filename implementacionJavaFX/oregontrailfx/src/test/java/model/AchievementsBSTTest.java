package model;

import org.junit.jupiter.api.Test;
import org.oniteam.oregontrailfx.model.Achivement;
import org.oniteam.oregontrailfx.model.NodeAchivement;
import org.oniteam.oregontrailfx.model.TreeAchivement;

import static org.junit.jupiter.api.Assertions.*;

public class AchievementsBSTTest {

    private TreeAchivement bst;
    private NodeAchivement a1;
    private NodeAchivement a2;

    void setupStage8() {
        bst = new TreeAchivement();
        Achivement p= new Achivement(10,"Primer disparo", "el jugador disparo un arma cualquiera por primera vez en el juego");
        Achivement k= new Achivement(30, "Sobrevive Montañas","el jugador fue capaz de pasar el escenario de las montañas por primera vez");
        a1 = new NodeAchivement(p, null, null);
        a2 = new NodeAchivement(k, null,null);
    }

    @Test
    void testInsertInOrder() {
        // arrange
        setupStage8();

        // act
        bst.insert(a1);
        bst.insert(a2);
        String traversal = bst.inOrder(); // suponiendo que devuelve los nombres concatenados

        // assert
        assertTrue(traversal.contains("Primer disparo") && traversal.contains("Sobrevive Montañas"),
                "El árbol debe contener ambos logros");
        assertTrue(traversal.indexOf("Primer disparo") < traversal.indexOf("Sobrevive Montañas"),
                "El recorrido in-order debe mantener el orden ascendente por puntaje");
    }

    @Test
    void testSearchAchievementByScore() {
        // arrange
        setupStage8();
        bst.insert(a1);
        bst.insert(a2);

        // act
        NodeAchivement found = bst.search(a1);

        // assert
        assertNotNull(found);
        assertEquals("Sobrevive Montañas", found.getValue().getNameAchivement(),
                "Debe devolver el logro correspondiente al puntaje buscado");
    }

    /**
     * aun no se puede llevar a cabo esta prueba
     * debido a que es javaFX para que se pueda llevar con exito
     */
    @Test
    void testOpenWindowDisplaysTree() {
        // arrange
        setupStage8();
        bst.insert(a1);
        bst.insert(a2);

        // act
        boolean opened = bst.openWindow();

        // assert
        assertTrue(opened, "Debe abrir una ventana con la representación del árbol");
    }
}

