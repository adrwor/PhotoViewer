package apps.aw.simplephotos.java.treenavigator.tree;

import java.util.Collections;
import java.util.List;


public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

    private final boolean sortChildren;
    private final Node<T> parent;
    private List<Node<T>> children;
    private T data;

    public Node(Node<T> parent, List<Node<T>> children, T data, boolean sortChildren) {
        this.parent = parent;
        this.children = children;
        this.data = data;
        this.sortChildren = sortChildren;
        if(sortChildren) {
            sortChildren();
        }
    }

    public void addChildWithData(T data) {
        assert (this.children != null);
        this.children.add(new Node<T>(this, null, data, true));
        if(sortChildren) {
            sortChildren();
        }
    }

    public void addChildWithData(T data, int position) {
        assert (this.children != null);
        this.children.add(position, new Node<T>(this, null, data, true));
        if(sortChildren) {
            sortChildren();
        }
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean hasAtLeastOneChild() {
        return getChildren() != null && !getChildren().isEmpty();
    }

    public void sortChildren() {
        Collections.sort(children);
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
        if(sortChildren) {
            sortChildren();
        }
    }

    public Node<T> getParent() {
        return parent;
    }

    @Override
    public int compareTo(Node<T> o) {
        return this.getData().compareTo(o.getData());
    }
}
