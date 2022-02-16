package apps.aw.simplephotos.domain_layer.model.Tree;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class Node<T> {

    private final Node<T> parent;
    private List<Node<T>> children;
    private @NonNull T data;

    public Node(Node<T> parent, List<Node<T>> children, @NonNull T data) {
        this.parent = parent;
        this.children = children;
        this.data = data;
    }

    public void addChild(T data) {

        assert (this.children != null);
        this.children.add(new Node<T>(this, null, data));
    }

    @NonNull
    public T getData() {
        return data;
    }

    public void setData(@NonNull T data) {
        this.data = data;
    }

    public boolean hasChildren() {
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
