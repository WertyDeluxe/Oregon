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
     * este metodo insertar o agregar en el arbol del jugador de logros
     *
     * @param node
     */
    public void insert(NodeAchivement node){
        if(rootPlayer == null){
            rootPlayer = node;
        }else{
            if(rootPlayer.getLeft()==null&& rootPlayer.getValue().compareTo(node.getValue())>0){
                rootPlayer.setLeft(node);
            }else if(rootPlayer.getRight()==null&& rootPlayer.getValue().compareTo(node.getValue())<0){
                rootPlayer.setRight(node);
            }else if(rootPlayer.getLeft()!=null && rootPlayer.getValue().compareTo(node.getValue())>0){
                insert(rootPlayer.getLeft(), node);
            }else if(rootPlayer.getRight()!=null && rootPlayer.getValue().compareTo(node.getValue())<0){
                insert(rootPlayer.getRight(), node);
            }

        }
    }

    /**
     * descripcion: este es un metodo auxiliar el cual es recursivo y busca donde se puede agregar el nodo
     * el cual queremos agregar en el arbol, en base a las condiciones de mayor o menor (izquierda y derecha)
     * @param node
     * @param current
     */
    private void insert(NodeAchivement node, NodeAchivement current){
        if (current.getValue().compareTo(node.getValue()) > 0){
            // Caso Base: la izquierda es Null
            if(current.getLeft() == null){
                current.setLeft(node);
            }
            // Caso Recursivo: Se debe seguir buscando
            else {
                insert(current.getLeft(), node);
            }
        }
        // El valor del Node es mayor que el valor del nodo actual (current)
        // se agrega a la derecha del current
        else if(current.getValue().compareTo(node.getValue()) < 0){
            if(current.getRight() == null){
                current.setRight(node);
            }
            else {
                insert(current.getRight(), node);
            }
        }
        else {
            // No agregamos el nodo
        }
    }

    /**
     * descripcion: este metodo busca un nodo que se le fue dado como parametro y primero valida si
     * la raiz es igual a null o no, si no llama a su metodo auxiliar para encontrar el nodo,
     * sino lo encuentra retornara un null
     * @param node
     * @return
     */
    public NodeAchivement search(NodeAchivement node){
        NodeAchivement foundNode=null;
        boolean found = false;
        // Caso Base : is empty tree
        if(rootPlayer == null){
            foundNode = null;
        }
        // Caso recursivo
        else{
            foundNode=search(rootPlayer, node);
        }
        return foundNode;
    }

    /**
     * descripcion: metodo auxiliar del search recibe el nodo a buscar y el actual donde esta parado
     * y en basea eso si el current es distinto de null, hace las correspondientes verificaciones y en base a eso
     * se hace un llamado o no
     * @param current
     * @param element
     * @return
     */
    private NodeAchivement search(NodeAchivement current, NodeAchivement element){
        NodeAchivement found = null;
        if(current != null){
            if (current.getValue().getDifficulty() == element.getValue().getDifficulty()){
                found = current;
            }
            else if(current.getValue().compareTo(element.getValue()) > 0){
                found = search(current.getLeft(), element);
            }
            else if (current.getValue().compareTo(element.getValue()) < 0){
                found = search(current.getRight(), element);
            }
        }

        return found;
    }

    /**
     * descripcion utiliza el algoritmo recursivo inorder para asi
     * retornar en String cocatenado la informacion de los nodos
     * @return
     */
    public String inOrder(){
        String msj = "";

        if (rootPlayer != null){
            msj = inOrder(rootPlayer);
        }
        else {
            msj = "el arbol de logros del jugador esta vacio";
        }
        return msj;
    }

    /**
     * descripcion: metodo auxiliar para el algoritmo de recorrido inorder
     * @param curent
     * @return
     */
    private String inOrder(NodeAchivement curent){
        // Caso recursio
        if(curent != null){
            // Recorrido por izquerda           |   Valor actual          |     Recorrido por derecha
            return inOrder(curent.getLeft()) + " " + curent.getValue().toString() + " " + inOrder(curent.getRight());
        }
        // Caso base
        else {
            return "";
        }
    }

    /**
     * descripcion: printear el arbol con inorder pero con la raiz de todos los logros
     * posibles
     * @return
     */
    public String inordenRootAllAchivements(){
        String msj = "";
        if (rootAllAchivements != null){
            msj=inOrder(rootAllAchivements);
        }else{
            msj = "el arbol de logros esta vacio";
        }
        return msj;
    }

    /**
     * descripcion metodo intertar pero para este caso sera para la raiz de todos los logros
     * @param node
     */
    public void insertInAllAchivement(NodeAchivement node){
        if(rootAllAchivements == null){
            rootAllAchivements = node;
        }else{
            if(rootAllAchivements.getLeft()==null&& rootAllAchivements.getValue().compareTo(node.getValue())>0){
                rootAllAchivements.setLeft(node);
            }else if(rootAllAchivements.getRight()==null&& rootAllAchivements.getValue().compareTo(node.getValue())<0){
                rootAllAchivements.setRight(node);
            }else if(rootAllAchivements.getLeft()!=null && rootAllAchivements.getValue().compareTo(node.getValue())>0){
                insert(rootAllAchivements.getLeft(), node);
            }else if(rootAllAchivements.getRight()!=null && rootAllAchivements.getValue().compareTo(node.getValue())<0){
                insert(rootAllAchivements.getRight(), node);
            }

        }
    }








}
