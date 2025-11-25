package model;

import org.junit.jupiter.api.Test;
import org.oniteam.oregontrailfx.model.Inventory;
import org.oniteam.oregontrailfx.model.ItemInventory;
import org.oniteam.oregontrailfx.model.Player;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory inv;
    private ItemInventory comida;
    private ItemInventory cura;
    private ItemInventory mun;
    private Player p;

    void setupStage5(){
        inv = new Inventory(3);                  // capacidad 3
        comida = ItemInventory.food(1);          // comida que da +1 efecto al usar
        cura   = ItemInventory.medkit(1);        // botiquín que cura +1
        mun    = ItemInventory.ammo("rifle", 3); // 3 balas rifle (efecto 0)
        p      = new Player(3.0);                // jugador empieza con 3 corazones/vida

        inv.add(comida);
        inv.add(cura); // en este punto tengo 2/3 ocupados
    }

    @Test
    void test1addammowithspaceininventory() {
        //arrange
        setupStage5();

        // act
        boolean agregado = inv.add(mun);

        // assert
        assertTrue(agregado, "La munición debe poder guardarse porque hay espacio disponible");
        assertEquals(3, inv.size(), "El inventario ahora debe tener 3 ítems");
        assertTrue(inv.contains(mun), "El inventario debe contener la munición agregada");
        assertTrue(inv.isFull(), "El inventario debe estar lleno");
    }

    @Test
    void test2AddfoodwithinventoryFull(){
        //arrange
        setupStage5();

        // pre: actualmente tengo comida, cura; lo voy a llenar con mun
        inv.add(mun); // ahora queda 3/3 (lleno)
        ItemInventory comidaExtra = ItemInventory.food(1);

        // act 1: intentar agregar estando lleno
        boolean agregadoInicial = inv.add(comidaExtra);   // esperado: false

        // gestión de espacio: remover la comida original
        boolean eliminado = inv.remove(comida);

        // act 2: volver a intentar
        boolean agregadoFinal = inv.add(comidaExtra);     // ahora sí debería agregar

        // assert
        assertFalse(agregadoInicial, "No debe agregar si está lleno hasta gestionar espacio");
        assertTrue(eliminado, "La gestión (remover/descartar) debe ser exitosa");
        assertTrue(agregadoFinal, "Debe poder agregar después de liberar un slot");
        assertEquals(3, inv.size(), "La capacidad ocupada debe mantenerse en 3 ítems");
        assertTrue(inv.contains(comidaExtra), "El inventario debe contener el nuevo ítem");
        assertFalse(inv.contains(comida), "El ítem descartado ya no debe estar");
    }

    @Test
    void test3UsemedincreseTheHearts(){
        //arrange
        setupStage5();

        double vidaHearts = p.gethearts();
        int sizeinventario = inv.size();

        // act: usar cura sobre el jugador
        boolean usado = inv.use(cura, p);

        // assert
        assertTrue(usado, "El medkit debe poder usarse");
        assertEquals(vidaHearts + 1, p.getVida(), "La vida del jugador debe aumentar en 1");
        assertEquals(sizeinventario - 1, inv.size(), "El medkit debe consumirse y reducir el inventario");
        assertFalse(inv.contains(cura), "El medkit usado no debe permanecer en el inventario");
    }
}


