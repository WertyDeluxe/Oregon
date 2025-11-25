package org.oniteam.oregontrailfx.model;

import org.oniteam.oregontrailfx.structures.ListaEnlazada;


import java.util.Iterator;


public class Inventory {

    private ListaEnlazada<ItemInventory> items;
    private int capacity;

    public Inventory(int capacity) {
        this.capacity = capacity;
        this.items = new ListaEnlazada<>();
    }

    public boolean add(ItemInventory item) {
        if (isFull()) {
            return false;
        }
        items.addLast(item);
        return true;
    }

    public boolean remove(ItemInventory item) {
        return items.remove(item);
    }

    public int size() {
        return items.size();
    }

    public boolean isFull() {
        return items.size() >= capacity;
    }

    public boolean contains(ItemInventory item) {
        Iterator<ItemInventory> it = items.iterator();
        while (it.hasNext()) {
            ItemInventory actual = it.next();
            if (actual.equals(item)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Usa un item del inventario sobre el jugador.
     * Caso del test: usar un botiquin  para curar.
     *
     * @param item ItemInventory que se quiere usar
     * @param p    jugador objetivo
     * @return true si se uso, false si no estaba o no tenia cantidad
     */
    public boolean use(ItemInventory item, Player p) {
        Iterator<ItemInventory> it = items.iterator();
        while (it.hasNext()) {
            ItemInventory actual = it.next();

            if (actual.equals(item)) {
                int efecto = actual.consumirUnaUnidad();

                if (actual.getTipo() == TypeItem.MEDICINA && efecto > 0) {
                    p.heal(efecto);
                }

                if (actual.estaVacio()) {
                    items.remove(actual);
                }

                return true;
            }
        }
        return false;
    }

    public ListaEnlazada<ItemInventory> getItems() {
        return items;
    }


    // Convierte la lista enlazada en un arreglo de tamaño exacto.

    private ItemInventory[] toArray() {
        int n = items.size();
        ItemInventory[] arr = new ItemInventory[n];
        int i = 0;
        Iterator<ItemInventory> it = items.iterator();
        while (it.hasNext ()) {
            arr[i++] = it.next();
        }
        return arr;
    }


    private void fromArray(ItemInventory[] arr) {
        ListaEnlazada<ItemInventory> items = new ListaEnlazada<>();
        for (ItemInventory ii : arr) {
            this.items.addLast(ii);
        }
        this.items = items;
    }

    /**
     * Metodo binarySearchByType
     * Busca un item por tipo usando busqueda binaria.
     * PRECONDICION: el inventario debe estar ORDENADO por tipo.
     *
     * @param goalType el tipo a buscar
     * @return el ItemInventory encontrado o null si no existe
     */
    public ItemInventory binarySearchByType(TypeItem goalType) {
        // 1. aseguramos orden
        sortByTypeBubble();

        // 2. trabajamos sobre array
        ItemInventory[] arr = toArray();
        int start = 0;
        int end = arr.length - 1;

        while (start <= end) {
            int mid = (start + end) / 2;
            TypeItem currentType = arr[mid].getTipo();

            if (currentType == goalType) {
                return arr[mid];
            } else {
                // comparamos por nombre del enum
                int cmp = currentType.toString().compareTo(goalType.toString());
                if (cmp > 0) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
        }
        return null;
    }


    /**
     * Metodo: sortByNameInsertion
     * Ordena los items por prioridad de recurso
     *
     * Pre: inventario inicializado
     *
     * post: items quedan ordenados por prioridad
     *
     */
    public void sortByPriorityInsertion() {
        if (items.size() <= 1) {
            return;
        }

        ItemInventory[] arr = toArray();

        for (int i = 1; i < arr.length; i++) {
            ItemInventory current = arr[i];
            int j = i - 1;

            while (j >= 0 && getPriority(arr[j]) > getPriority(current)) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = current;
        }

        fromArray(arr);
    }

    /**
     * Devuelve la prioridad numérica de un item según su tipo.
     * 1 = MEDICINA, 2 = COMIDA, 3 = MUNICION, 4= otros
     */

    private int getPriority(ItemInventory item) {
        if (item.getTipo() == TypeItem.MEDICINA) return 1;
        if (item.getTipo() == TypeItem.COMIDA) return 2;
        if (item.getTipo() == TypeItem.MUNICION) return 3;
        return 4;
    }

    /**
     * Metodo linearSearchByName
     * Busca un item por nombre usando busqueda lineal.
     * PRECONDICION: name no debe ser null.
     *
     * @param name el nombre del item a buscar
     * @return el ItemInventory encontrado o null si no existe
     */
    public ItemInventory linearSearchByName(String name) {
        Iterator<ItemInventory> it = items.iterator();

        while (it.hasNext()) {
            ItemInventory actual = it.next();
            if (actual.getNombre().equalsIgnoreCase(name)) {
                return actual;
            }
        }

        return null;
    }



    /**
     * Metodo sortByQuantitySelection
     * Ordena el inventario por cantidad de recursos usando Selection Sort.
     * Los items  con menor cantidad quedan primero (recursos escasos).
     *
     * Algoritmo: Selection Sort
     *
     * PRECONDICION: el inventario debe estar INICIALIZADO y puede contener cualquier numero de items.
     * Cada ItemInventory debe tener cantidad mayor o igual a 0.
     *
     * post: los items quedan ordenados por cantidad ascendente
     */
    public void sortByQuantitySelection() {
        if (items.size() <= 1) {
            return;
        }

        ItemInventory[] arr = toArray();

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].getCantidad() < arr[minIndex].getCantidad()) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                ItemInventory tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
            }
        }

        fromArray(arr);
    }


    /**
     * Metodo sortByTypeBubble
     * Ordena el inventario por tipo de item usando Bubble Sort.
     * Orden alfabetico: COMIDA < MEDICINA < MUNICION
     *
     *
     * La lista enlazada se convierte a ArrayList porque:
     * - LinkedList tiene acceso O(n) por indice
     * - ArrayList tiene acceso O(1) por indice
     * - Mantiene el algoritmo en O(n²) en lugar de O(n³)
     *
     * PRECONDICION: el inventario debe estar INICIALIZADO y puede contener cualquier numero de items.
     * Cada ItemInventory debe tener un tipo valido (no null).
     *
     * @return no retorna nada (void). Los items quedan ordenados alfabeticamente por tipo.
     *
     * pos: los items quedan ordenados alfabeticamente por tipo
     */
    public void sortByTypeBubble() {
        if (items.size() <= 1) {
            return;
        }

        ItemInventory[] arr = toArray();
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                String typeA = arr[j].getTipo().toString();
                String typeB = arr[j + 1].getTipo().toString();

                if (typeA.compareTo(typeB) > 0) {
                    ItemInventory temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        fromArray(arr);
    }



}
