package apps.aw.simplephotos.domain_layer.model.Tree;

import java.util.ArrayList;

public class Tree<T> {

    private Node<T> root;

    public Tree(T rootData) {
        // TODO: should parent of root be null or itself?
        root = new Node<T>(null, null, rootData);
    }

    public Node<T> getRoot() {
        return root;
    }
}
