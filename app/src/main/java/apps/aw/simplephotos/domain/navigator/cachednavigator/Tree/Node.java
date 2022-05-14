package apps.aw.simplephotos.domain.navigator.cachednavigator.Tree;

import java.util.List;


public class Node<T> {

    private final Node<T> parent;
    private List<Node<T>> children;
    private T data;

    public Node(Node<T> parent, List<Node<T>> children, T data) {
        this.parent = parent;
        this.children = children;
        this.data = data;
    }

    public void addChildWithData(T data) {
        assert (this.children != null);
        this.children.add(new Node<T>(this, null, data));
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

    public List<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }

    public Node<T> getParent() {
        return parent;
    }
}
