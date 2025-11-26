package org.oniteam.oregontrailfx.model;

public class TreeAchivement {
    private NodeAchivement rootPlayer;
    private NodeAchivement rootAllAchivements;

    public NodeAchivement getRootPlayer() {
        return rootPlayer;
    }

    public void setRootPlayer(NodeAchivement rootPlayer) {
        this.rootPlayer = rootPlayer;
    }

    public NodeAchivement getRootAllAchivements() {
        return rootAllAchivements;
    }

    public void setRootAllAchivements(NodeAchivement rootAllAchivements) {
        this.rootAllAchivements = rootAllAchivements;
    }

    /**
     * Inserta un logro en el árbol del jugador (logros desbloqueados).
     */
    public void insert(NodeAchivement node){
        if(rootPlayer == null){
            rootPlayer = node;
        } else {
            insertRecursive(rootPlayer, node);
        }
    }

    /**
     * Método auxiliar recursivo para insertar en el árbol del jugador.
     */
    private void insertRecursive(NodeAchivement current, NodeAchivement node){
        if (current.getValue().compareTo(node.getValue()) > 0){
            if(current.getLeft() == null){
                current.setLeft(node);
            } else {
                insertRecursive(current.getLeft(), node);
            }
        } else if(current.getValue().compareTo(node.getValue()) < 0){
            if(current.getRight() == null){
                current.setRight(node);
            } else {
                insertRecursive(current.getRight(), node);
            }
        }
    }

    /**
     * ✅ FIX: Inserta un logro en el árbol de TODOS los logros disponibles.
     * Ahora usa insertInAllAchivementRecursive() en vez de insert().
     */
    public void insertInAllAchivement(NodeAchivement node){
        if(rootAllAchivements == null){
            rootAllAchivements = node;
        } else {
            insertInAllAchivementRecursive(rootAllAchivements, node);
        }
    }

    /**
     * ✅ FIX: Método auxiliar recursivo para insertar en rootAllAchivements.
     * Antes llamaba a insert() que insertaba en rootPlayer (BUG).
     */
    private void insertInAllAchivementRecursive(NodeAchivement current, NodeAchivement node){
        if (current.getValue().compareTo(node.getValue()) > 0){
            if(current.getLeft() == null){
                current.setLeft(node);
            } else {
                insertInAllAchivementRecursive(current.getLeft(), node);
            }
        } else if(current.getValue().compareTo(node.getValue()) < 0){
            if(current.getRight() == null){
                current.setRight(node);
            } else {
                insertInAllAchivementRecursive(current.getRight(), node);
            }
        }
    }

    /**
     * Busca un logro en el árbol del jugador.
     */
    public NodeAchivement search(NodeAchivement node){
        if(rootPlayer == null){
            return null;
        }
        return searchRecursive(rootPlayer, node);
    }

    /**
     * Método auxiliar recursivo para búsqueda en el árbol del jugador.
     */
    private NodeAchivement searchRecursive(NodeAchivement current, NodeAchivement element){
        if(current == null){
            return null;
        }

        if (current.getValue().getDifficulty() == element.getValue().getDifficulty()){
            return current;
        } else if(current.getValue().compareTo(element.getValue()) > 0){
            return searchRecursive(current.getLeft(), element);
        } else {
            return searchRecursive(current.getRight(), element);
        }
    }

    /**
     * Recorrido InOrder del árbol del jugador (logros desbloqueados).
     */
    public String inOrder(){
        if (rootPlayer == null){
            return "El árbol de logros del jugador está vacío";
        }
        return inOrderRecursive(rootPlayer);
    }

    /**
     * Método auxiliar recursivo para InOrder.
     */
    private String inOrderRecursive(NodeAchivement current){
        if(current == null){
            return "";
        }
        return inOrderRecursive(current.getLeft()) + " " +
                current.getValue().toString() + " " +
                inOrderRecursive(current.getRight());
    }

    /**
     * Recorrido InOrder del árbol de TODOS los logros disponibles.
     */
    public String inordenRootAllAchivements(){
        if (rootAllAchivements == null){
            return "El árbol de todos los logros está vacío";
        }
        return inOrderRecursive(rootAllAchivements);
    }

    /**
     * Cuenta cuántos logros ha desbloqueado el jugador.
     */
    public int countPlayerAchivements(){
        return countNodes(rootPlayer);
    }

    /**
     * Cuenta el total de logros disponibles en el juego.
     */
    public int countAllAchivements(){
        return countNodes(rootAllAchivements);
    }

    /**
     * Método auxiliar recursivo para contar nodos.
     */
    private int countNodes(NodeAchivement node){
        if(node == null){
            return 0;
        }
        return 1 + countNodes(node.getLeft()) + countNodes(node.getRight());
    }
}