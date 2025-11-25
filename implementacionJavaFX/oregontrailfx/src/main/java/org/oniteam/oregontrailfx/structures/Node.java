package org.oniteam.oregontrailfx.structures;

public class Node<T> {
    T valor;
    Node<T> next;
    Node<T> prev;

    Node(T valor){
        this.valor = valor;
        this.next = null;
        this.prev = null;
    }
}
