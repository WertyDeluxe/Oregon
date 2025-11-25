package org.oniteam.oregontrailfx.structures;

import java.util.Iterator;

public class ListaEnlazada<T> {

    private Node<T> head;
    private int size;

    public void addLast(T element) {
        Node<T> nuevo = new Node<>(element);
        if (head == null) {
            head = nuevo;
        } else {
            Node<T> actual = head;
            while (actual.next != null) {
                actual = actual.next;
            }
            actual.next = nuevo;
        }
        size++;
    }

    public void addFirst(T element) {
        Node<T> nuevo = new Node<>(element);
        nuevo.next = head;
        head = nuevo;
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index " + index);
        }
        Node<T> actual = head;
        for (int i = 0; i < index; i++) {
            actual = actual.next;
        }
        return actual.valor;
    }

    public boolean remove(T element) {
        if (head == null) return false;

        if ((head.valor == null && element == null) ||
                (head.valor != null && head.valor.equals(element))) {
            head = head.next;
            size--;
            return true;
        }

        Node<T> actual = head;
        while (actual.next != null) {
            if ((actual.next.valor == null && element == null) ||
                    (actual.next.valor != null && actual.next.valor.equals(element))) {

                actual.next = actual.next.next;
                size--;
                return true;
            }
            actual = actual.next;
        }
        return false;
    }

    public T removeFirst() {
        if (head == null) return null;
        T valor = head.valor;
        head = head.next;
        size--;
        return valor;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> actual = head;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                T v = actual.valor;
                actual = actual.next;
                return v;
            }
        };
    }

}
