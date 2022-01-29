package apps.aw.simplephotos.domain_layer.model;

import apps.aw.simplephotos.domain_layer.model.Tree.Node;
import apps.aw.simplephotos.domain_layer.model.Tree.Tree;

/**
 * Represents the filesystem, and keeps track of last selected files.
 */
public class FileSystemModel {

    private Tree<FileSystemNode> tree;

    public FileSystemModel(FileSystemNode root) {
        tree = new Tree<>(root);
    }

    public Node<FileSystemNode> getRootNode() {
        return tree.getRoot();
    }

}

