package org.oniteam.oregontrailfx.model;

import java.util.Objects;

public class ItemInventory {

    private final String nombre;
    private final TypeItem tipo;
    private final String descripcion;
    private int cantidad;
    private final int valorEfecto;

    public ItemInventory(String nombre, TypeItem tipo, String descripcion, int cantidad, int valorEfecto) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser vacio");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo no puede ser null");
        }
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }

        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.valorEfecto = valorEfecto;
    }

    public static ItemInventory food(int cantidad) {
        return new ItemInventory(
                "Comida",
                TypeItem.COMIDA,
                "Ración de comida basica",
                cantidad,
                1
        );
    }

    public static ItemInventory medkit(int cantidad) {
        return new ItemInventory(
                "Botiquin",
                TypeItem.MEDICINA,
                "Cura al jugador",
                cantidad,
                1
        );
    }

    public static ItemInventory ammo(String arma, int cantidad) {
        return new ItemInventory(
                "Munición " + arma,
                TypeItem.MUNICION,
                "Balas para " + arma,
                cantidad,
                0
        );
    }

    public String getNombre() {
        return nombre;
    }

    public TypeItem getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getValorEfecto() {
        return valorEfecto;
    }

    /**
     * Usa una unidad de este item (por ejemplo comer 1 ración,
     * aplicar 1 vendaje, gastar 1 bala).
     *
     * @return el efecto que generó (ej: cuánta vida cura, o 0 si es munición)
     *
     * Lanza IllegalStateException si ya no queda cantidad.
     */
    public int consumirUnaUnidad() {
        if (cantidad <= 0) {
            throw new IllegalStateException("No quedan unidades de " + nombre);
        }
        cantidad--;
        return valorEfecto;
    }

    /**
     * Agrega más unidades del mismo item (stackear).
     */
    public void agregarUnidades(int extra) {
        if (extra < 0) {
            throw new IllegalArgumentException("extra no puede ser negativo");
        }
        cantidad += extra;
    }

    public boolean estaVacio() {
        return cantidad == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemInventory)) return false;
        ItemInventory that = (ItemInventory) o;
        return Objects.equals(nombre, that.nombre) &&
                tipo == that.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, tipo);
    }

    @Override
    public String toString() {
        return nombre + " x" + cantidad + " (" + tipo + ")";
    }
}
