package apps.aw.simplephotos.domain_layer.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import apps.aw.simplephotos.domain_layer.model.Tree.Node;

/**
 * Class which provides methods to move from directory to directory, as well as list folders and files.
 */
public class FileSystemNavigator {

    private FileSystemModel fileSystemModel;
    private Node<FileSystemNode> currentNode;

    /**
     * Constructor
     */
    public FileSystemNavigator(FileModel rootFileModel) {
        setFileSystemModel(new FileSystemModel(new FileSystemNode(rootFileModel)));
        setCurrentNode(getFileSystemModel().getRootNode());
    }

    public FileSystemModel getFileSystemModel() {
        return fileSystemModel;
    }

    public void setFileSystemModel(FileSystemModel fileSystemModel) {
        this.fileSystemModel = fileSystemModel;
    }

    public Node<FileSystemNode> getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node<FileSystemNode> node) {
        currentNode = node;
    }

    public boolean currentNodeIsDirectory() throws SecurityException {
        return getCurrentNode().getData().getFileModel().isDirectory();
    }

    /**
     *
     * @return the parent node, null if current node is root.
     */
    public Node<FileSystemNode> getParentNode() {
        return getCurrentNode().getParent();
    }

    /**
     * Sets current Directory to parent Directory (if there is a parent directory).
     */
    public void goToParentNode() {
        // TODO: maybe do these checks one level above, in the InteractorImpl?
        Node<FileSystemNode> parent = getParentNode();
        if(parent != null) {
            setCurrentNode(parent);
        }
    }

    public void goToFocusedChildNode() {
        // TODO: maybe do these checks one level above, in the InteractorImpl?
        if(currentNodeHasChildren() && getFocusedChildNode().hasChildren()) {
            setCurrentNode(getFocusedChildNode());
        }
    }

    public Node<FileSystemNode> getFocusedChildNode() {
        assert hasChildren(currentNode);
        int focusedChildPosition = getCurrentNode().getData().getFocusedChildPosition();
        return getChildrenOfCurrentNode().get(focusedChildPosition);    // index out of bounds
    }

    public void focusChildNode(int position) {
        getCurrentNode().getData().setFocusedChildPosition(position);
    }

    public int getChildNodeSelection() {
        return getCurrentNode().getData().getFocusedChildPosition();
    }

    public void focusNextChildNode() {
        int currentPosition = getCurrentNode().getData().getFocusedChildPosition();
        if(currentPosition < getChildrenOfCurrentNode().size() - 1) {
            getCurrentNode().getData().setFocusedChildPosition(currentPosition + 1);
        }
    }

    public void focusPreviousChildNode() {
        int currentPosition = getCurrentNode().getData().getFocusedChildPosition();
        if(currentPosition > 0) {
            getCurrentNode().getData().setFocusedChildPosition(currentPosition - 1);
        }
    }

    public List<Node<FileSystemNode>> getChildrenOfCurrentNode() {
        return getChildrenOfNode(getCurrentNode());
    }

    public boolean currentNodeHasChildren() {
        return hasChildren(currentNode);
    }

    public boolean hasChildren(Node<FileSystemNode> node) {
        List<Node<FileSystemNode>> children = getChildrenOfNode(node);
        return (children != null) && (children.size() > 0);
    }

    public List<Node<FileSystemNode>> getChildrenOfNode(Node<FileSystemNode> node) {
        syncChildrenOfNode(node);
        return node.getChildren();
    }

    /**
     * If the given node denotes a directory, the subFiles are loaded from disk and stored in the
     * tree. If the given node is not a directory, nothing happens.
     * @param node
     */
    public void syncChildrenOfNode(Node<FileSystemNode> node) {
        if(node.getData().getFileModel().isDirectory() && (node.getChildren() == null)) {
            reloadChildrenFromDisk(node);
        }
    }

    /**
     * Sets the children of the given node if the corresponding file is a directory, or sets
     * children to null if the given file is not a directory.
     * @param node
     */
    public void reloadChildrenFromDisk(@NonNull Node<FileSystemNode> node) {
        if(node.getData().getFileModel().isDirectory()) {
            List<FileModel> subfiles;
            subfiles = FileSystemReader.getSubFileModels(node.getData().getFileModel().getFile());
            node.setChildren(new ArrayList<>());
            for (FileModel fileModel: subfiles) {
                node.addChild(new FileSystemNode(fileModel));
            }
        }
        else {
            node.setChildren(null);
        }
    }
}