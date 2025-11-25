package org.oniteam.oregontrailfx.model;

public class ListEnemy {

    private NodeEnemy first;

    /**
     * Método: ListEnemy()
     * inicializa la lista de enemigos vacía
     * Poscondición: la referencia 'first' queda en null
     */
    public ListEnemy() {
        first = null;
    }

    public NodeEnemy getFirst() {
        return first;
    }



    /**
     * Método: addEnemy
     * agrega un enemigo al final de la lista (simplemente enlazada).
     * Precondición:
     *   - data es diferente de null
     *
     * @param data enemigo que se va a agregar
     *
     * Poscondición:
     *   - si la lista estaba vacía, first apunta al nuevo nodo
     *   - si la lista tenía datos, el nuevo enemigo queda de último
     */
    public void addEnemy(Enemy data) {
        NodeEnemy node = new NodeEnemy(data);

        // Caso base: lista vacía
        if (first == null) {
            first = node;
        } else {
            // caso: ya hay al menos un nodo
            if (first.getNext() == null) {
                first.setNext(node);
            } else {
                NodeEnemy current = first.getNext();
                while (current.getNext() != null) {
                    current = current.getNext();
                }
                current.setNext(node);
            }
        }
    }


    /**
     * Método: delete
     * elimina de la lista el primer enemigo que sea igual al que llega.
     * Precondición:
     *   - goal diferente de  null
     *
     * @param goal enemigo que se quiere borrar
     * @return true si se eliminó, false si no estaba
     * Poscondición: si el enemigo estaba en la lista, la lista queda encadenada sin ese nodo
     */
    public boolean delete(Enemy goal) {
        boolean deleted = false;

        // caso base: lista vacía
        if (first == null) {
            deleted = false;
        }
        // caso base: eliminar el primero
        else if (first.getData().equals(goal)) {
            first = first.getNext();
            deleted = true;
        }
        // caso recursivo: eliminar en el resto de la lista
        else {
            deleted = delete(first, first.getNext(), goal);
        }

        return deleted;
    }

    /**
     * Método auxiliar recursivo para borrar.
     * Recorre la lista con puntero al anterior y al actual.
     */
    private boolean delete(NodeEnemy previous, NodeEnemy current, Enemy goal) {
        boolean deleted = false;

        if (current != null && current.getData().equals(goal)) {
            previous.setNext(current.getNext());
            current.setNext(null);
            deleted = true;
        } else if (current != null) {
            deleted = delete(current, current.getNext(), goal);
        }

        return deleted;
    }

    /**
     * Método: printList
     * recorre la lista y arma un String con los enemigos.
     * Precondición: ninguna.
     *
     *   @return un String con todos los enemigos en orden
     * Poscondición: no modifica la lista.
     */
    public String printList() {
        String msj = "";
        NodeEnemy current = first;
        while (current != null) {
            Enemy e = current.getData();
            msj += e.getTypeEnemy () + " ,";
            current = current.getNext();
        }
        return msj;
    }
    public int contEnemies() {
        int cont = 0;
        if (first != null) {
            NodeEnemy current = first;
            while (current != null) {
                current = current.getNext();
                cont ++;
            }
        }else{
            cont = 0;
        }
        return  cont;
    }






}
