package org.oniteam.oregontrailfx.model;

public class NodeAchivement {
    private Achivement value;
    private NodeAchivement left;
    private NodeAchivement right;

    public NodeAchivement(Achivement value, NodeAchivement left, NodeAchivement right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public Achivement getValue() {
        return value;
    }

    public void setValue(Achivement value) {
        this.value = value;
    }

    public NodeAchivement getLeft() {
        return left;
    }

    public void setLeft(NodeAchivement left) {
        this.left = left;
    }

    public NodeAchivement getRight() {
        return right;
    }

    public void setRight(NodeAchivement right) {
        this.right = right;
    }
}
