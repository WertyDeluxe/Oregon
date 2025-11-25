package org.oniteam.oregontrailfx.model;

public class NodeEnemy {

    Enemy data;
    NodeEnemy next;

    public NodeEnemy(Enemy data) {
        this.data = data;
        this.next = null;
    }

    public Enemy getData() {
        return data;
    }

    public void setData(Enemy data) {
        this.data = data;
    }

    public NodeEnemy getNext() {
        return next;
    }

    public void setNext(NodeEnemy next) {
        this.next = next;
    }
}
