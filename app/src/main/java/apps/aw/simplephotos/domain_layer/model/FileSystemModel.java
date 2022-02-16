package apps.aw.simplephotos.domain_layer.model;

import apps.aw.simplephotos.domain_layer.model.Tree.Node;

/**
 * Represents the filesystem, and keeps track of last selected files.
 */
public class FileSystemModel {

    private final Node<FileSystemNode> rootNode;

    public FileSystemModel(FileSystemNode rootData) {
        rootNode = new Node<>(null, null, rootData);
    }

    public Node<FileSystemNode> getRootNode() {
        return rootNode;
    }

}

